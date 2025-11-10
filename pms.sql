-- phpMyAdmin SQL Dump
-- version 5.1.2
-- https://www.phpmyadmin.net/
--
-- Gép: localhost:3306
-- Létrehozás ideje: 2025. Nov 10. 19:22
-- Kiszolgáló verziója: 5.7.24
-- PHP verzió: 8.3.1

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Adatbázis: `pms`
--

DELIMITER $$
--
-- Eljárások
--
CREATE DEFINER=`root`@`localhost` PROCEDURE `checkReservationForAdminReservation` (IN `dateIN` DATE, IN `startHourIN` INT, IN `endHourIN` INT)   BEGIN
	SELECT r.id FROM reservation r 
    INNER JOIN reserved_hour rh 
    ON r.reserved_hour_id = rh.id 
    INNER JOIN reserved_date rd 
    ON rh.date_id = rd.id 
    WHERE
    
    ((rd.date = dateIN AND startHourIN BETWEEN rh.start AND rh.end)
    OR
    (rd.date = dateIN AND endHourIN BETWEEN rh.start AND rh.end)
    OR 
    (rd.date = dateIN AND rh.start BETWEEN startHourIN AND endHourIN)
    OR 
    (rd.date = dateIN AND rh.end BETWEEN startHourIN AND endHourIN))
    AND 
    r.is_canceled = 0
    
    ;
    
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `getAllAdmin` ()   BEGIN
	SELECT * FROM user WHERE user.role_id = 2 AND user.is_deleted = 0;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `getAllEmail` ()   BEGIN
	SELECT user.email FROM user;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `getAllReservationEmail` ()   BEGIN
	SELECT reservation.email FROM reservation;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `getAllReservationsBetweenIntervallum` (IN `startDateIN` DATETIME, IN `endDateIN` DATETIME)   BEGIN
	SELECT r.id FROM reservation r 
    INNER JOIN reserved_hour rh ON 
    r.reserved_hour_id = rh.id 
    INNER JOIN reserved_date rd ON 
    rh.date_id = rd.id
    WHERE 
    r.is_canceled = 0 AND
    (rd.date BETWEEN startDateIN AND endDateIN);
   
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `getReservationByDate` (IN `dateIN` DATE)   BEGIN
	SELECT 
 	r.id
    FROM reservation r
    INNER JOIN reserved_hour rh 
    ON r.reserved_hour_id = rh.id
    INNER JOIN reserved_date rd 
    ON rh.date_id = rd.id
    WHERE 
    rd.date = dateIN;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `getReservationByUserId` (IN `userIdIN` INT)   BEGIN
	SELECT * FROM reservation WHERE reservation.user_id = userIdIN;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `getReservationsByEmail` (IN `emailIN` VARCHAR(100))   BEGIN
	SELECT * FROM reservation WHERE reservation.email = emailIN;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `getReservationsForAdminReservation` (IN `startDateIN` DATE, IN `endDateIN` DATE, IN `startHourIN` INT(2), IN `endHourIN` INT(2))   BEGIN
	SELECT r.id FROM reservation r 
    INNER JOIN reserved_hour rh ON 
    rh.id = r.reserved_hour_id 
    INNER JOIN reserved_date rd ON 
    rd.id = rh.date_id 
    
    WHERE 
    ((rd.date BETWEEN startDateIN AND endDateIN) AND (startHourIN BETWEEN rh.start AND rh.end))
    OR
	((rd.date BETWEEN startDateIN AND endDateIN) AND (endHourIN BETWEEN rh.start AND rh.end))
    OR
    ((rd.date BETWEEN startDateIN AND endDateIN) AND (rh.start BETWEEN startHourIN AND endHourIN))
    OR
    ((rd.date BETWEEN startDateIN AND endDateIN) AND (rh.end BETWEEN startHourIN AND endHourIN))
    ;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `getReservedDateBetweenTwoDateByDate` (IN `startDateIN` DATE, IN `endDateIN` DATE, IN `dateIN` DATE)   BEGIN
	SELECT * FROM reserved_date WHERE 
    reserved_date.date = dateIN AND 
    reserved_date.date BETWEEN startDateIN AND endDateIN; 
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `getReservedDateByDate` (IN `dateIN` DATETIME)   BEGIN
	SELECT * FROM reserved_date WHERE reserved_date.date = dateIN;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `getReservedDatesOfPeriod` (IN `startDateIN` DATE, IN `endDateIN` DATE)   BEGIN
	SELECT
    	*
    FROM reserved_date rd
    WHERE
    rd.date BETWEEN startDateIN AND endDateIN;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `getReservedHoursByDate` (IN `dateIN` DATE)   BEGIN
	SELECT 
	*
    FROM reserved_hour
    INNER JOIN reserved_date ON 
	reserved_hour.date_id = reserved_date.id 
    AND DAY(reserved_date.date) = DAY(dateIN);
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `getUserByEmail` (IN `emailIN` VARCHAR(100))   BEGIN
	SELECT * FROM user WHERE user.email = emailIN;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `getUserByUsername` (IN `usernameIN` VARCHAR(100))   BEGIN
	SELECT * FROM user 
    WHERE user.username = usernameIN;
END$$

DELIMITER ;

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `admin_details`
--

CREATE TABLE `admin_details` (
  `id` int(11) NOT NULL,
  `first_name` varchar(100) NOT NULL,
  `last_name` varchar(100) NOT NULL,
  `email` varchar(100) NOT NULL,
  `phone` varchar(100) NOT NULL,
  `user_id` int(11) NOT NULL,
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0',
  `deleted_at` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- A tábla adatainak kiíratása `admin_details`
--

INSERT INTO `admin_details` (`id`, `first_name`, `last_name`, `email`, `phone`, `user_id`, `is_deleted`, `deleted_at`) VALUES
(1, 'Halmai', 'Bence', 'bzhalmai@gmail.com', '706285232', 49, 0, NULL),
(2, 'Halmai', 'Bence', 'bzhalmai@gmail.com', '12345', 1, 0, NULL),
(3, 'asd', 'asd', 'asd@gmail.com', 'a', 3, 1, '2025-10-22 12:20:50');

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `device`
--

CREATE TABLE `device` (
  `id` int(11) NOT NULL,
  `name` varchar(100) NOT NULL,
  `category_id` int(11) NOT NULL,
  `amount` int(2) NOT NULL,
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0',
  `deleted_at` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- A tábla adatainak kiíratása `device`
--

INSERT INTO `device` (`id`, `name`, `category_id`, `amount`, `is_deleted`, `deleted_at`) VALUES
(3, 'mikrofon1', 1, 2, 0, NULL),
(4, 'mikrofon2', 1, 2, 0, NULL),
(5, 'gitar1', 2, 2, 0, NULL),
(6, 'gitar2', 2, 2, 0, NULL),
(7, 'mikrofon3', 1, 1, 0, NULL),
(8, 'mikrofon4', 1, 1, 0, NULL),
(9, 'mikrofon5', 1, 1, 0, NULL),
(10, 'gitar3', 2, 1, 0, NULL),
(11, 'gitar4', 2, 1, 0, NULL),
(12, 'gitar5', 2, 1, 0, NULL),
(13, 'erosito1', 3, 1, 0, NULL),
(14, 'erosito2', 3, 1, 0, NULL),
(15, 'erosito3', 3, 1, 0, NULL),
(16, 'erosito4', 3, 1, 0, NULL),
(17, 'erosito5', 3, 1, 0, NULL),
(18, 'zongora1', 4, 1, 0, NULL),
(19, 'zongora2', 4, 1, 0, NULL),
(20, 'zongora3', 4, 1, 0, NULL),
(21, 'zongora4', 4, 1, 0, NULL),
(22, 'zongora5', 4, 1, 0, NULL),
(23, 'dob1', 5, 1, 0, NULL),
(24, 'dob2', 5, 1, 0, NULL),
(25, 'dob3', 5, 1, 0, NULL),
(26, 'dob4', 5, 1, 0, NULL),
(27, 'dob5', 5, 1, 0, NULL),
(31, 'postTest1', 1, 1, 1, '2025-10-06 17:08:10'),
(32, 'updateTest', 2, 2, 1, '2025-10-06 17:07:31');

--
-- Eseményindítók `device`
--
DELIMITER $$
CREATE TRIGGER `checkFullDay` BEFORE INSERT ON `device` FOR EACH ROW BEGIN

END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `device_category`
--

CREATE TABLE `device_category` (
  `id` int(11) NOT NULL,
  `name` varchar(100) NOT NULL,
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0',
  `deleted_at` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- A tábla adatainak kiíratása `device_category`
--

INSERT INTO `device_category` (`id`, `name`, `is_deleted`, `deleted_at`) VALUES
(1, 'mikrofonok', 0, NULL),
(2, 'gitarok', 0, NULL),
(3, 'erositok', 0, NULL),
(4, 'zongorak', 0, NULL),
(5, 'dobok', 0, NULL),
(6, 'UpdateTest', 1, '2025-10-06 13:14:05'),
(7, 'postTest', 0, NULL);

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `device_reservation_type`
--

CREATE TABLE `device_reservation_type` (
  `id` int(11) NOT NULL,
  `reservation_tpye_id` int(11) NOT NULL,
  `device_id` int(11) NOT NULL,
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0',
  `deleted_at` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `event_type`
--

CREATE TABLE `event_type` (
  `id` int(11) NOT NULL,
  `name` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- A tábla adatainak kiíratása `event_type`
--

INSERT INTO `event_type` (`id`, `name`) VALUES
(1, 'Létrehozás'),
(2, 'Frissités'),
(3, 'Törlés');

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `gallery`
--

CREATE TABLE `gallery` (
  `id` int(11) NOT NULL,
  `photo_name` longtext NOT NULL,
  `photo_path` longtext NOT NULL,
  `placement` int(2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- A tábla adatainak kiíratása `gallery`
--

INSERT INTO `gallery` (`id`, `photo_name`, `photo_path`, `placement`) VALUES
(1, '1.jpg', 'assets/images/gallery/1.jpg', 1),
(2, '2.jpg', 'assets/images/gallery/2.jpg', 2),
(3, '3.jpg', 'assets/images/gallery/3.jpg', 3),
(4, '4.jpg', 'assets/images/gallery/4.jpg', 4),
(5, '5.jpg', 'assets/images/gallery/5.jpg', 5),
(6, '6.jpg', 'assets/images/gallery/6.jpg', 6),
(7, '7.jpg', 'assets/images/gallery/7.jpg', 7),
(8, '8.jpg', 'assets/images/gallery/8.jpg', 8);

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `history`
--

CREATE TABLE `history` (
  `id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `event_type_id` int(11) NOT NULL,
  `table_name` varchar(100) NOT NULL,
  `column_name` varchar(100) NOT NULL,
  `row_id` int(11) NOT NULL,
  `old_value` varchar(100) NOT NULL,
  `new_value` varchar(100) NOT NULL,
  `edited_at` datetime NOT NULL,
  `is_active` tinyint(1) NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `news`
--

CREATE TABLE `news` (
  `id` int(11) NOT NULL,
  `title` varchar(100) NOT NULL,
  `text` longtext NOT NULL,
  `banner_img_path` longtext,
  `writer_id` int(11) NOT NULL,
  `placement` int(2) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0',
  `deleted_at` datetime DEFAULT NULL,
  `last_edit_at` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- A tábla adatainak kiíratása `news`
--

INSERT INTO `news` (`id`, `title`, `text`, `banner_img_path`, `writer_id`, `placement`, `created_at`, `is_deleted`, `deleted_at`, `last_edit_at`) VALUES
(1, 'Új hangszerek érkeztek a terembe', 'Megérkeztek a legújabb hangszereink, amelyeket bárki kipróbálhat próba közben. A dobkészletet teljesen felújítottuk, valamint új gitárerősítőket szereztünk be. Így még jobb hangzást tudunk biztosítani a zenekaroknak. Gyertek el és teszteljétek őket elsőként!', NULL, 47, 1, '2025-08-23 11:19:02', 0, NULL, NULL),
(2, 'Akciós próbadíjak szeptemberben', 'Ebben a hónapban kedvezményes áron bérelhetitek a próbatermet. A hétköznapi délutáni sávokra 20% kedvezményt biztosítunk. Ha rendszeresen jártok, még további engedményeket is adunk. Ne hagyjátok ki a lehetőséget!', 'assets/images/news/placeholder.png', 1, 2, '2025-08-23 11:19:02', 0, NULL, NULL),
(3, 'Nyílt nap a próbateremben', 'Szeretettel várunk minden érdeklődőt a nyílt napunkon. Lehetőségetek lesz kipróbálni a termet és a hangszereket teljesen ingyen. A program során bemutatjuk a felszerelést és válaszolunk minden kérdésre. Gyere el, és hozd magaddal zenész barátaidat is!', 'assets/images/news/placeholder.png', 1, 3, '2025-08-23 11:19:38', 0, NULL, NULL),
(4, 'Új foglalási rendszer indult', 'Mostantól egyszerűbben és gyorsabban tudtok időpontot foglalni. Az online naptár segítségével azonnal látható, mikor szabad a terem. Így elkerülhetők a félreértések és ütközések. Próbáljátok ki, és foglaljatok pár kattintással!', 'assets/images/news/placeholder.png', 1, 3, '2025-08-27 08:07:08', 0, NULL, NULL),
(5, 'Koncert a próbaterem zenekaraival', 'A nálunk próbáló zenekarok közül többen fellépnek egy közös koncerten. Az esemény célja, hogy bemutassuk a helyi tehetségeket. A belépés ingyenes, mindenkit szeretettel várunk. Részletek hamarosan a weboldalunkon!', 'assets/images/news/placeholder.png', 1, 5, '2025-08-27 08:07:08', 0, NULL, NULL),
(9, 'updateTest', 'adsadad', 'a', 48, 0, '2025-10-05 16:22:06', 1, '2025-10-06 14:08:20', NULL),
(10, 'a', 'a', NULL, 48, 0, '2025-10-05 16:06:59', 1, '2025-10-05 18:28:52', NULL);

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `payment_method`
--

CREATE TABLE `payment_method` (
  `id` int(11) NOT NULL,
  `name` varchar(100) NOT NULL,
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0',
  `deleted_at` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- A tábla adatainak kiíratása `payment_method`
--

INSERT INTO `payment_method` (`id`, `name`, `is_deleted`, `deleted_at`) VALUES
(1, 'Kártyás', 0, NULL),
(2, 'Készpénz', 0, NULL),
(3, 'Revolut', 0, NULL);

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `phone_country_code`
--

CREATE TABLE `phone_country_code` (
  `id` int(11) NOT NULL,
  `country_code` int(3) NOT NULL,
  `country_name` varchar(150) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- A tábla adatainak kiíratása `phone_country_code`
--

INSERT INTO `phone_country_code` (`id`, `country_code`, `country_name`) VALUES
(1, 1, 'American Samoa'),
(2, 1, 'Anguilla'),
(3, 1, 'Antigua and Barbuda'),
(4, 1, 'Bahamas'),
(5, 1, 'Barbados'),
(6, 1, 'Bermuda'),
(7, 1, 'British Virgin Islands'),
(8, 1, 'Canada'),
(9, 1, 'Cayman Islands'),
(10, 1, 'Dominica'),
(11, 1, 'Dominican Republic'),
(12, 1, 'Grenada'),
(13, 1, 'Guam'),
(14, 1, 'Jamaica'),
(15, 1, 'Montserrat'),
(16, 1, 'Northern Mariana Islands'),
(17, 1, 'Puerto Rico'),
(18, 1, 'Saint Kitts and Nevis'),
(19, 1, 'Saint Lucia'),
(20, 1, 'Saint Vincent and the Grenadines'),
(21, 1, 'Trinidad and Tobago'),
(22, 1, 'Turks and Caicos Islands'),
(23, 1, 'United States of America'),
(24, 1, 'United States Virgin Islands'),
(25, 20, 'Egypt'),
(26, 212, 'Morocco'),
(27, 213, 'Algeria'),
(28, 216, 'Tunisia'),
(29, 218, 'Libya'),
(30, 220, 'Gambia'),
(31, 221, 'Senegal'),
(32, 222, 'Mauritania'),
(33, 223, 'Mali'),
(34, 224, 'Guinea'),
(35, 225, 'Côte d\'Ivoire'),
(36, 226, 'Burkina Faso'),
(37, 227, 'Niger'),
(38, 228, 'Togolese Republic'),
(39, 229, 'Benin'),
(40, 230, 'Mauritius'),
(41, 231, 'Liberia'),
(42, 232, 'Sierra Leone'),
(43, 233, 'Ghana'),
(44, 234, 'Nigeria'),
(45, 235, 'Chad'),
(46, 236, 'Central African Republic'),
(47, 237, 'Cameroon'),
(48, 238, 'Cape Verde'),
(49, 239, 'Sao Tome and Principe'),
(50, 240, 'Equatorial Guinea'),
(51, 241, 'Gabonese Republic'),
(52, 242, 'Congo'),
(53, 243, 'Democratic Republic of the Congo'),
(54, 244, 'Angola'),
(55, 245, 'Guinea-Bissau'),
(56, 246, 'Diego Garcia'),
(57, 247, 'Ascension'),
(58, 248, 'Seychelles'),
(59, 249, 'Sudan'),
(60, 250, 'Rwanda'),
(61, 251, 'Ethiopia'),
(62, 252, 'Somali Democratic Republic'),
(63, 253, 'Djibouti'),
(64, 254, 'Kenya'),
(65, 255, 'Tanzania'),
(66, 256, 'Uganda'),
(67, 257, 'Burundi'),
(68, 258, 'Mozambique'),
(69, 260, 'Zambia'),
(70, 261, 'Madagascar'),
(71, 262, 'French Departments and Territories in the Indian Ocean j'),
(72, 263, 'Zimbabwe'),
(73, 264, 'Namibia'),
(74, 265, 'Malawi'),
(75, 266, 'Lesotho'),
(76, 267, 'Botswana'),
(77, 268, 'Swaziland'),
(78, 269, 'Comoros'),
(79, 269, 'Mayotte'),
(80, 27, 'South Africa'),
(81, 290, 'Saint Helena a'),
(82, 290, 'Tristan da Cunha a'),
(83, 291, 'Eritrea'),
(84, 297, 'Aruba'),
(85, 298, 'Faroe Islands'),
(86, 299, 'Greenland (Denmark)'),
(87, 30, 'Greece'),
(88, 31, 'Netherlands'),
(89, 32, 'Belgium'),
(90, 33, 'France'),
(91, 34, 'Spain'),
(92, 350, 'Gibraltar'),
(93, 351, 'Portugal'),
(94, 352, 'Luxembourg'),
(95, 353, 'Ireland'),
(96, 354, 'Iceland'),
(97, 355, 'Albania'),
(98, 356, 'Malta'),
(99, 357, 'Cyprus'),
(100, 358, 'Finland'),
(101, 359, 'Bulgaria'),
(102, 36, 'Hungary'),
(103, 370, 'Lithuania'),
(104, 371, 'Latvia'),
(105, 372, 'Estonia'),
(106, 373, 'Moldova'),
(107, 374, 'Armenia'),
(108, 375, 'Belarus'),
(109, 376, 'Andorra'),
(110, 377, 'Monaco'),
(111, 378, 'San Marino'),
(112, 379, 'Vatican City State f'),
(113, 380, 'Ukraine'),
(114, 381, 'Serbia '),
(115, 382, 'Montenegro '),
(116, 385, 'Croatia '),
(117, 386, 'Slovenia '),
(118, 387, 'Bosnia and Herzegovina'),
(119, 388, 'Group of countries, shared code'),
(120, 389, 'The Former Yugoslav Republic of Macedonia'),
(121, 39, 'Italy'),
(122, 39, 'Vatican City State'),
(123, 40, 'Romania'),
(124, 41, 'Switzerland'),
(125, 420, 'Czech Republic'),
(126, 421, 'Slovak Republic'),
(127, 423, 'Liechtenstein'),
(128, 43, 'Austria'),
(129, 44, 'United Kingdom of Great Britain and Northern Ireland'),
(130, 45, 'Denmark'),
(131, 46, 'Sweden'),
(132, 47, 'Norway'),
(133, 48, 'Poland'),
(134, 49, 'Germany'),
(135, 500, 'Falkland Islands'),
(136, 501, 'Belize'),
(137, 502, 'Guatemala'),
(138, 503, 'El Salvador'),
(139, 504, 'Honduras'),
(140, 505, 'Nicaragua'),
(141, 506, 'Costa Rica'),
(142, 507, 'Panama '),
(143, 508, 'Saint Pierre and Miquelon'),
(144, 509, 'Haiti '),
(145, 51, 'Peru'),
(146, 52, 'Mexico'),
(147, 53, 'Cuba'),
(148, 54, 'Argentine Republic'),
(149, 55, 'Brazil'),
(150, 56, 'Chile'),
(151, 57, 'Colombia '),
(152, 58, 'Venezuela '),
(153, 590, 'Guadeloupe '),
(154, 591, 'Bolivia '),
(155, 592, 'Guyana'),
(156, 593, 'Ecuador'),
(157, 594, 'French Guiana '),
(158, 595, 'Paraguay '),
(159, 596, 'Martinique '),
(160, 597, 'Suriname '),
(161, 598, 'Uruguay'),
(162, 599, 'Netherlands Antilles'),
(163, 60, 'Malaysia'),
(164, 61, 'Australia'),
(165, 62, 'Indonesia '),
(166, 63, 'Philippines'),
(167, 64, 'New Zealand'),
(168, 65, 'Singapore '),
(169, 66, 'Thailand'),
(170, 670, 'Democratic Republic of Timor-Leste'),
(171, 672, 'Australian External Territories g'),
(172, 673, 'Brunei Darussalam'),
(173, 674, 'Nauru '),
(174, 675, 'Papua New Guinea'),
(175, 676, 'Tonga'),
(176, 677, 'Solomon Islands'),
(177, 678, 'Vanuatu '),
(178, 679, 'Fiji '),
(179, 680, 'Palau '),
(180, 681, 'Wallis and Futuna '),
(181, 682, 'Cook Islands'),
(182, 683, 'Niue'),
(183, 685, 'Samoa'),
(184, 686, 'Kiribati '),
(185, 687, 'New Caledonia '),
(186, 688, 'Tuvalu'),
(187, 689, 'French Polynesia '),
(188, 690, 'Tokelau'),
(189, 691, 'Micronesia'),
(190, 692, 'Marshall Islands'),
(191, 7, 'Kazakhstan'),
(192, 7, 'Russian Federation'),
(193, 81, 'Japan'),
(194, 82, 'Korea '),
(195, 84, 'Viet Nam'),
(196, 850, 'Democratic People\'s Republic of Korea'),
(197, 852, 'Hong Kong, China'),
(198, 853, 'Macao, China'),
(199, 855, 'Cambodia'),
(200, 856, 'Lao People\'s Democratic Republic'),
(201, 86, 'China'),
(202, 870, 'Inmarsat SNAC'),
(203, 880, 'Bangladesh'),
(204, 886, 'Taiwan, China'),
(205, 90, 'Turkey'),
(206, 91, 'India '),
(207, 92, 'Pakistan'),
(208, 93, 'Afghanistan'),
(209, 94, 'Sri Lanka'),
(210, 95, 'Myanmar'),
(211, 960, 'Maldives '),
(212, 961, 'Lebanon'),
(213, 962, 'Jordan'),
(214, 963, 'Syrian Arab Republic'),
(215, 964, 'Iraq'),
(216, 965, 'Kuwait'),
(217, 966, 'Saudi Arabia'),
(218, 967, 'Yemen'),
(219, 968, 'Oman'),
(220, 971, 'United Arab Emirates h'),
(221, 972, 'Israel'),
(222, 973, 'Bahrain'),
(223, 974, 'Qatar'),
(224, 975, 'Bhutan'),
(225, 976, 'Mongolia'),
(226, 977, 'Nepal'),
(227, 98, 'Iran'),
(228, 992, 'Tajikistan'),
(229, 993, 'Turkmenistan'),
(230, 994, 'Azerbaijani Republic'),
(231, 995, 'Georgia'),
(232, 996, 'Kyrgyz Republic'),
(233, 998, 'Uzbekistan');

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `reservation`
--

CREATE TABLE `reservation` (
  `id` int(11) NOT NULL,
  `first_name` varchar(100) NOT NULL,
  `last_name` varchar(100) NOT NULL,
  `email` varchar(100) NOT NULL,
  `phone_country_code_id` int(11) NOT NULL DEFAULT '102',
  `phone_number` varchar(9) NOT NULL,
  `comment` longtext,
  `cancel_v_code` longtext,
  `reservation_type_id` int(11) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  `payment_method_id` int(11) DEFAULT NULL,
  `status_id` int(11) NOT NULL DEFAULT '1',
  `reserved_hour_id` int(11) NOT NULL,
  `reserved_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `is_canceled` tinyint(1) DEFAULT NULL,
  `canceled_at` datetime DEFAULT NULL,
  `canceled_by` int(11) DEFAULT NULL,
  `canceler_email` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `reservation_type`
--

CREATE TABLE `reservation_type` (
  `id` int(11) NOT NULL,
  `name` varchar(100) NOT NULL,
  `price` int(6) NOT NULL,
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0',
  `deleted_at` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- A tábla adatainak kiíratása `reservation_type`
--

INSERT INTO `reservation_type` (`id`, `name`, `price`, `is_deleted`, `deleted_at`) VALUES
(1, 'Egyéni Gyakorlás', 2000, 0, NULL),
(2, 'Zenekari Próba', 4000, 0, NULL),
(3, 'Önálló felvételek', 6000, 0, NULL),
(4, 'kategória 4', 1500, 0, NULL),
(5, 'kategória 5', 5500, 0, NULL);

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `reserved_date`
--

CREATE TABLE `reserved_date` (
  `id` int(11) NOT NULL,
  `date` date NOT NULL,
  `is_holiday` tinyint(1) NOT NULL DEFAULT '0',
  `is_closed` tinyint(1) NOT NULL DEFAULT '0',
  `is_full` tinyint(1) NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `reserved_hour`
--

CREATE TABLE `reserved_hour` (
  `id` int(11) NOT NULL,
  `start` int(2) NOT NULL,
  `end` int(2) NOT NULL,
  `date_id` int(11) NOT NULL,
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0',
  `deleted_at` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `review`
--

CREATE TABLE `review` (
  `id` int(11) NOT NULL,
  `author_id` int(11) NOT NULL,
  `review_text` longtext NOT NULL,
  `rating` double NOT NULL,
  `is_anonymous` tinyint(1) NOT NULL DEFAULT '0',
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0',
  `deleted_at` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- A tábla adatainak kiíratása `review`
--

INSERT INTO `review` (`id`, `author_id`, `review_text`, `rating`, `is_anonymous`, `created_at`, `is_deleted`, `deleted_at`) VALUES
(1, 1, 'Nagyon jól felszerelt próbaterem, minden rendben működött. A foglalás gyors és egyszerű volt, biztosan jövünk még!', 5, 0, '2025-09-08 09:59:25', 0, NULL),
(2, 47, 'A terem hangulata és akusztikája kiváló, pont amire egy zenekarnak szüksége van. A felszerelés minőségi, és minden tisztán, rendezetten várt minket. Nagy előny, hogy a foglalás rugalmasan intézhető, így könnyen be tudtuk illeszteni a próbát a sűrű hetünkbe.', 2, 0, '2025-09-08 10:21:46', 0, NULL),
(3, 26, 'Már több próbateremben jártunk a városban, de ez az egyik legjobb élményünk eddig. A hely tágas, kényelmes, a hangszerek és az erősítők hibátlan állapotban voltak, a dob is tökéletesen beállítva. Érezhető, hogy a tulajdonos szívvel-lélekkel foglalkozik a hellyel. Nagy plusz, hogy a környéken könnyű parkolni, így nem kellett cipekednünk messziről. Az ár-érték arány is teljesen korrekt, szóval biztosan rendszeres vendégek leszünk.', 2, 0, '2025-09-08 10:21:46', 0, NULL),
(4, 2, 'Imádtuk! A terem hangulata inspiráló, minden tiszta és profi. Már az első percben úgy éreztem, mintha stúdióban lennénk. A csapatom is teljesen odavolt, biztosan visszajáró vendégek leszünk!', 3.5, 0, '2025-09-08 10:21:46', 0, NULL),
(5, 29, 'A helyszín nagyon jó, az akusztika is rendben van. Egyetlen apróság, hogy a légkondi lehetne erősebb, mert nyáron gyorsan felmelegszik a terem. Ezen kívül minden tökéletes volt, szívesen ajánlom más zenekaroknak is.', 2, 0, '2025-09-08 10:21:46', 0, NULL),
(6, 50, 'Nagyon király a hely, minden cucc pöpecül működik. Nincs macera a foglalással, simán ment minden. Full jó vibe, ide tuti még visszajövünk jammelni!', 2, 0, '2025-09-08 10:21:46', 0, NULL),
(7, 48, 'postTest12', 2.5, 0, '2025-10-07 02:47:52', 0, NULL),
(10, 49, 'testReview', 2.5, 0, '2025-11-03 19:05:34', 0, NULL);

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `review_like_history`
--

CREATE TABLE `review_like_history` (
  `id` int(11) NOT NULL,
  `review_id` int(11) NOT NULL,
  `like_type` varchar(10) NOT NULL,
  `user_id` int(11) NOT NULL,
  `like_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- A tábla adatainak kiíratása `review_like_history`
--

INSERT INTO `review_like_history` (`id`, `review_id`, `like_type`, `user_id`, `like_at`) VALUES
(2, 7, 'like', 48, '2025-11-03 17:11:18'),
(3, 6, 'like', 48, '2025-11-03 17:13:38'),
(4, 5, 'like', 48, '2025-11-03 17:15:08'),
(5, 2, 'dislike', 48, '2025-11-03 17:17:00'),
(6, 3, 'like', 48, '2025-11-03 18:48:45'),
(7, 4, 'like', 48, '2025-11-03 19:32:04'),
(10, 10, 'like', 49, '2025-11-03 20:05:41');

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `role`
--

CREATE TABLE `role` (
  `id` int(11) NOT NULL,
  `name` varchar(100) NOT NULL,
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0',
  `deleted_at` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- A tábla adatainak kiíratása `role`
--

INSERT INTO `role` (`id`, `name`, `is_deleted`, `deleted_at`) VALUES
(1, 'ROLE_user', 0, NULL),
(2, 'ROLE_admin', 0, NULL),
(3, 'ROLE_superAdmin', 0, NULL);

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `rule`
--

CREATE TABLE `rule` (
  `id` int(11) NOT NULL,
  `text` longtext NOT NULL,
  `last_edit_at` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- A tábla adatainak kiíratása `rule`
--

INSERT INTO `rule` (`id`, `text`, `last_edit_at`) VALUES
(1, '<h1>Próbaterem-használati Szabályzat</h1>\nEz a szabályzat a(z) [Próbaterem neve] (a továbbiakban: „Próbaterem”) rendeltetésszerű használatát, a bérlők jogait és kötelezettségeit, valamint a helyiség és felszerelések védelmét hivatott biztosítani. A szabályzat minden bérlőre és használóra vonatkozik. A próbaterem bérlésével a felhasználó automatikusan elfogadja a jelen szabályzat rendelkezéseit.\nÁltalános rendelkezések\nA próbaterem kizárólag zenészek és zenekarok részére áll rendelkezésre, akik a helyiséget próbálás, gyakorlás, felkészülés céljából veszik igénybe.\nA próbaterem használata bérleti díj ellenében történik, amelynek mértékét és fizetési módját a szolgáltató határozza meg.\nA próbaterem kulcsát vagy belépési jogosultságát csak az előre egyeztetett és díjat megfizető bérlő kaphatja meg.\nA próbaterem használata csak a lefoglalt időpontban engedélyezett. Az időkeret túllépése külön díjfizetéssel járhat.\n2. Nyitvatartás és foglalás\nA próbaterem előzetes időpont-egyeztetés alapján foglalható.\nA foglalást lemondani legalább 24 órával a kezdés előtt lehet. Későbbi lemondás esetén a bérleti díj felszámítható.\nA pontos kezdési és befejezési idő betartása kötelező, mivel más zenekarok is foglalhatják a termet.\nA próbaterem munkaszüneti napokon és ünnepnapokon is nyitva lehet, de ez minden esetben külön egyeztetést igényel.\nMagatartási szabályok\nA próbaterem területén tilos a dohányzás, nyílt láng használata és bármilyen tűz- vagy robbanásveszélyes anyag behozatala.\nAlkohol és kábítószer fogyasztása szigorúan tilos. Ittas vagy bódult állapotban a próbaterem nem használható.\nA bérlők kötelesek a helyiséget rendeltetésszerűen használni, másokat nem zavarni, a zajkibocsátási előírásokat betartani.\nA helyiségben a berendezési tárgyakat, hangszereket, technikai eszközöket megóvni köteles minden bérlő.\nBármilyen rongálás, meghibásodás vagy hiány észlelése esetén azt azonnal jelezni kell az üzemeltető felé.\nFelszerelések használata\nA próbateremben található hangtechnikai berendezések, dob, erősítők, mikrofonok és egyéb felszerelések használata kizárólag rendeltetésszerűen történhet.\nA bérlők kötelesek a saját hangszereiket és kiegészítő eszközeiket gondosan kezelni.\nSaját hangtechnikai eszköz beállítása, bekötése csak az üzemeltető engedélyével történhet.\nAz eszközök nem vihetők ki a próbateremből az üzemeltető külön engedélye nélkül.\nTisztaság és rend\nA bérlők kötelesek a próbaterem rendjét és tisztaságát megőrizni.\nA próbaterem elhagyásakor a szemetet ki kell vinni, az üres üdítős palackokat, ételmaradékokat el kell távolítani.\nA próbatermet olyan állapotban kell átadni, amilyenben a bérlő átvette.\nBiztonság\nA próbateremben mindenki saját felelősségére tartózkodik.\nAz üzemeltető nem vállal felelősséget az esetleges balesetekért, személyi sérülésekért, vagyoni károkért, illetve a helyiségben hagyott személyes tárgyakért.\nA bérlő köteles gondoskodni arról, hogy a próbatermet az idő lejártakor szabályosan bezárja.\nTilos a vészkijáratokat, elektromos berendezéseket vagy tűzvédelmi eszközöket akadályozni.\nFelelősség és kártérítés\nA bérlő teljes anyagi felelősséggel tartozik a próbaterem és a benne található felszerelések épségéért.\nRongálás vagy nem rendeltetésszerű használat esetén a kárt a bérlő köteles megtéríteni.\nHa több zenész vagy zenekar használja egyidejűleg a termet, a felelősség egyetemleges.\nZáró rendelkezések\nA szabályzat be nem tartása a bérleti jogviszony azonnali megszüntetését vonhatja maga után.\nAz üzemeltető jogosult a szabályzatot bármikor módosítani, amelyről a bérlőket értesíti.\nA próbaterem bérlése és használata egyben a szabályzat elfogadását jelenti.\n\n', NULL);

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `status`
--

CREATE TABLE `status` (
  `id` int(11) NOT NULL,
  `name` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- A tábla adatainak kiíratása `status`
--

INSERT INTO `status` (`id`, `name`) VALUES
(1, 'Aktív'),
(2, 'Befejezett'),
(3, 'Lemondott');

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `user`
--

CREATE TABLE `user` (
  `id` int(11) NOT NULL,
  `username` varchar(100) NOT NULL,
  `email` varchar(100) NOT NULL,
  `password` longtext NOT NULL,
  `pfp_path` longtext NOT NULL,
  `is_notification_about_news` tinyint(1) NOT NULL DEFAULT '0',
  `role_id` int(11) NOT NULL DEFAULT '1',
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `last_login` datetime DEFAULT NULL,
  `is_deleted` tinyint(1) DEFAULT '1',
  `deleted_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- A tábla adatainak kiíratása `user`
--

INSERT INTO `user` (`id`, `username`, `email`, `password`, `pfp_path`, `is_notification_about_news`, `role_id`, `created_at`, `last_login`, `is_deleted`, `deleted_at`) VALUES
(1, 'test', 'test@gmail.com', 'asd', 'assets/placeholder.png', 0, 2, '2025-08-23 04:45:44', NULL, 0, NULL),
(2, 'testAdmin', 'testAdmin', '{noop}testAdmin', 'assets/placeholder.png', 0, 1, '2025-08-23 04:50:02', NULL, 0, NULL),
(3, 'testSuperAdmin', 'testSuperAdmin', '{noop}testSuperAdmin', 'assets/placeholder.png', 0, 2, '2025-08-23 04:50:02', NULL, 0, NULL),
(26, 'test4', 'test4@gmail.com', '{noop}asd', 'assets/placeholder.png', 0, 1, '2025-09-07 12:15:02', NULL, 1, NULL),
(29, 'test9', 'test9@gmail.com', '{noop}test5.Asd', 'assets/placeholder.png', 0, 1, '2025-09-17 15:47:25', NULL, 0, NULL),
(42, 'test23', 'adsa@gmail.cim', '{noop}asdAsd1.', 'assets/placeholder.png', 0, 1, '2025-09-20 16:18:03', NULL, 0, NULL),
(44, 'testasd', 'testassd@gmail.com', 'test5.Asd', 'assets/placeholder.png', 0, 1, '2025-09-24 10:03:22', NULL, 0, NULL),
(46, 'tesasdtasd2', 'testassdasd@gmail.com', '$argon2id$v=19$m=4096,t=3,p=1$OcUDw0z5AWhUccvzwFD2rw$LpNlyUFn9b6gLk8p8V+u5D+7sgP2YMeHPgKfVZFXhxE', 'assets/placeholder.png', 0, 1, '2025-09-24 10:07:39', NULL, 0, NULL),
(47, 'securityTest7621', 'testSec@gmail.com', '$argon2id$v=19$m=4096,t=3,p=1$BNwvMe4SC6uq+GPX93MqQA$tzij6Pp9XCKLN5r12S5rJs82GUF80/Ef2uW0+1w6NQs', 'assets/placeholder.png', 0, 1, '2025-08-23 04:45:44', '2025-11-03 21:17:39', 0, NULL),
(48, 'securityTest2', 'testSec2@gmail.com', '$argon2id$v=19$m=4096,t=3,p=1$iiG5S5IaM744EyTdONr2Iw$2WyJWijaInLTOM3Gn/jJTe3u3+mPdsW3sJe+PV/yVak', 'assets/placeholder.png', 0, 2, '2025-08-23 04:45:44', '2025-11-04 14:57:12', 0, NULL),
(49, 'securityTest3', 'testSec3@gmail.com', '$argon2id$v=19$m=4096,t=3,p=1$Gl1mOgXOHCm4JGC/oyJkrg$zbQXZ2wsOMFZrYUNhQSmlvXLuCctK6tQZL45nx4JqAg', 'assets\\images\\pfp\\93137338.png', 0, 3, '2025-08-23 04:45:44', '2025-11-10 19:07:36', 0, NULL),
(50, 'securityTest4', 'testSec4@gmail.com', '$argon2id$v=19$m=4096,t=3,p=1$pzasMKopB4YrFgBTesVvbA$oBGlWaxs/xvQPBz9DvwT9hfJmMp/uaVmlQ9W+u9ZbHM', 'assets/placeholder.png', 0, 3, '2025-08-23 04:45:44', NULL, 0, NULL),
(51, 'Hbence102', 'bzhalmai2@gmail.com', '$argon2id$v=19$m=4096,t=3,p=1$u7z7om52Z0b2bK4s1Ur0ag$Iajf7Y/fODVN9HyJ1xW0cns24CuadsCyZYgDJpQHGmY', 'assets/placeholder.png', 0, 3, '2025-08-23 04:45:44', NULL, 0, NULL),
(52, 'ads', 'da@gmail.com', '$argon2id$v=19$m=4096,t=3,p=1$LAcsPL6w8qOmubkZliXzEA$vYcDtVIQ92uk1yF/vf5nEfc/H88ecH5/9h2CK6Er85E', 'asd', 0, 1, '2025-10-06 10:04:11', NULL, 0, NULL),
(53, 'Hbence10', 'bzhalmai@gmail.com', '$argon2id$v=19$m=4096,t=3,p=1$hlOwH9m6xK1Dd32KvM4rrw$GynOLkKK/ajBhRmq6Hw6Cle1ziSzo98RpV1sdE7dmzM', 'assets/placeholder.png', 0, 1, '2025-11-06 07:37:47', '2025-11-06 08:38:06', 0, NULL);

--
-- Indexek a kiírt táblákhoz
--

--
-- A tábla indexei `admin_details`
--
ALTER TABLE `admin_details`
  ADD PRIMARY KEY (`id`),
  ADD KEY `admin_user` (`user_id`);

--
-- A tábla indexei `device`
--
ALTER TABLE `device`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `name` (`name`),
  ADD KEY `category` (`category_id`);

--
-- A tábla indexei `device_category`
--
ALTER TABLE `device_category`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `name` (`name`);

--
-- A tábla indexei `device_reservation_type`
--
ALTER TABLE `device_reservation_type`
  ADD PRIMARY KEY (`id`),
  ADD KEY `reservation` (`reservation_tpye_id`);

--
-- A tábla indexei `event_type`
--
ALTER TABLE `event_type`
  ADD PRIMARY KEY (`id`);

--
-- A tábla indexei `gallery`
--
ALTER TABLE `gallery`
  ADD PRIMARY KEY (`id`);

--
-- A tábla indexei `history`
--
ALTER TABLE `history`
  ADD PRIMARY KEY (`id`),
  ADD KEY `event_type` (`event_type_id`),
  ADD KEY `userId` (`user_id`);

--
-- A tábla indexei `news`
--
ALTER TABLE `news`
  ADD PRIMARY KEY (`id`),
  ADD KEY `writer` (`writer_id`);

--
-- A tábla indexei `payment_method`
--
ALTER TABLE `payment_method`
  ADD PRIMARY KEY (`id`);

--
-- A tábla indexei `phone_country_code`
--
ALTER TABLE `phone_country_code`
  ADD PRIMARY KEY (`id`);

--
-- A tábla indexei `reservation`
--
ALTER TABLE `reservation`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `reserved_hour_id` (`reserved_hour_id`),
  ADD KEY `user` (`user_id`),
  ADD KEY `reservation_type` (`reservation_type_id`),
  ADD KEY `cancelled` (`canceled_by`),
  ADD KEY `payment_method` (`payment_method_id`),
  ADD KEY `status` (`status_id`),
  ADD KEY `phone_country_code` (`phone_country_code_id`);

--
-- A tábla indexei `reservation_type`
--
ALTER TABLE `reservation_type`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `name` (`name`);

--
-- A tábla indexei `reserved_date`
--
ALTER TABLE `reserved_date`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `date` (`date`);

--
-- A tábla indexei `reserved_hour`
--
ALTER TABLE `reserved_hour`
  ADD PRIMARY KEY (`id`),
  ADD KEY `date` (`date_id`);

--
-- A tábla indexei `review`
--
ALTER TABLE `review`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `author_id` (`author_id`);

--
-- A tábla indexei `review_like_history`
--
ALTER TABLE `review_like_history`
  ADD PRIMARY KEY (`id`),
  ADD KEY `review` (`review_id`),
  ADD KEY `liker_user` (`user_id`);

--
-- A tábla indexei `role`
--
ALTER TABLE `role`
  ADD PRIMARY KEY (`id`);

--
-- A tábla indexei `rule`
--
ALTER TABLE `rule`
  ADD PRIMARY KEY (`id`);

--
-- A tábla indexei `status`
--
ALTER TABLE `status`
  ADD PRIMARY KEY (`id`);

--
-- A tábla indexei `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `email` (`email`),
  ADD UNIQUE KEY `username` (`username`),
  ADD KEY `role` (`role_id`);

--
-- A kiírt táblák AUTO_INCREMENT értéke
--

--
-- AUTO_INCREMENT a táblához `admin_details`
--
ALTER TABLE `admin_details`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT a táblához `device`
--
ALTER TABLE `device`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=33;

--
-- AUTO_INCREMENT a táblához `device_category`
--
ALTER TABLE `device_category`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT a táblához `device_reservation_type`
--
ALTER TABLE `device_reservation_type`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT a táblához `event_type`
--
ALTER TABLE `event_type`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT a táblához `gallery`
--
ALTER TABLE `gallery`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT a táblához `history`
--
ALTER TABLE `history`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT a táblához `news`
--
ALTER TABLE `news`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT a táblához `payment_method`
--
ALTER TABLE `payment_method`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT a táblához `phone_country_code`
--
ALTER TABLE `phone_country_code`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=234;

--
-- AUTO_INCREMENT a táblához `reservation`
--
ALTER TABLE `reservation`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=212;

--
-- AUTO_INCREMENT a táblához `reservation_type`
--
ALTER TABLE `reservation_type`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT a táblához `reserved_date`
--
ALTER TABLE `reserved_date`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=190;

--
-- AUTO_INCREMENT a táblához `reserved_hour`
--
ALTER TABLE `reserved_hour`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=195;

--
-- AUTO_INCREMENT a táblához `review`
--
ALTER TABLE `review`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT a táblához `review_like_history`
--
ALTER TABLE `review_like_history`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT a táblához `role`
--
ALTER TABLE `role`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT a táblához `rule`
--
ALTER TABLE `rule`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT a táblához `status`
--
ALTER TABLE `status`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT a táblához `user`
--
ALTER TABLE `user`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=54;

--
-- Megkötések a kiírt táblákhoz
--

--
-- Megkötések a táblához `admin_details`
--
ALTER TABLE `admin_details`
  ADD CONSTRAINT `admin_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`);

--
-- Megkötések a táblához `device`
--
ALTER TABLE `device`
  ADD CONSTRAINT `category` FOREIGN KEY (`category_id`) REFERENCES `device_category` (`id`);

--
-- Megkötések a táblához `device_reservation_type`
--
ALTER TABLE `device_reservation_type`
  ADD CONSTRAINT `device` FOREIGN KEY (`id`) REFERENCES `device` (`id`),
  ADD CONSTRAINT `reservation` FOREIGN KEY (`reservation_tpye_id`) REFERENCES `reservation_type` (`id`);

--
-- Megkötések a táblához `history`
--
ALTER TABLE `history`
  ADD CONSTRAINT `event_type` FOREIGN KEY (`event_type_id`) REFERENCES `event_type` (`id`),
  ADD CONSTRAINT `userId` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`);

--
-- Megkötések a táblához `news`
--
ALTER TABLE `news`
  ADD CONSTRAINT `writer` FOREIGN KEY (`writer_id`) REFERENCES `user` (`id`);

--
-- Megkötések a táblához `reservation`
--
ALTER TABLE `reservation`
  ADD CONSTRAINT `cancelled` FOREIGN KEY (`canceled_by`) REFERENCES `user` (`id`),
  ADD CONSTRAINT `payment_method` FOREIGN KEY (`payment_method_id`) REFERENCES `payment_method` (`id`),
  ADD CONSTRAINT `phone_country_code` FOREIGN KEY (`phone_country_code_id`) REFERENCES `phone_country_code` (`id`),
  ADD CONSTRAINT `reservation_h` FOREIGN KEY (`reserved_hour_id`) REFERENCES `reserved_hour` (`id`),
  ADD CONSTRAINT `reservation_type` FOREIGN KEY (`reservation_type_id`) REFERENCES `reservation_type` (`id`),
  ADD CONSTRAINT `status` FOREIGN KEY (`status_id`) REFERENCES `status` (`id`),
  ADD CONSTRAINT `user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`);

--
-- Megkötések a táblához `reserved_hour`
--
ALTER TABLE `reserved_hour`
  ADD CONSTRAINT `date` FOREIGN KEY (`date_id`) REFERENCES `reserved_date` (`id`);

--
-- Megkötések a táblához `review`
--
ALTER TABLE `review`
  ADD CONSTRAINT `author` FOREIGN KEY (`author_id`) REFERENCES `user` (`id`);

--
-- Megkötések a táblához `review_like_history`
--
ALTER TABLE `review_like_history`
  ADD CONSTRAINT `liker_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  ADD CONSTRAINT `review` FOREIGN KEY (`review_id`) REFERENCES `review` (`id`);

--
-- Megkötések a táblához `user`
--
ALTER TABLE `user`
  ADD CONSTRAINT `role` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
