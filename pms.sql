-- phpMyAdmin SQL Dump
-- version 5.1.2
-- https://www.phpmyadmin.net/
--
-- Gép: localhost:3306
-- Létrehozás ideje: 2025. Sze 02. 18:28
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
CREATE DEFINER=`root`@`localhost` PROCEDURE `addReview` (IN `userIdIN` INT, IN `reviewTextIN` LONGTEXT, IN `ratingIN` DOUBLE, IN `isAnonymusIN` INT)   BEGIN
	INSERT INTO `review`(`author_id`, `review_text`, `rating`, `is_anonymus`) VALUES (userIdIN, reviewTextIN, ratingIN, isAnonymusIN);
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `cancelReservation` (IN `userIdIN` INT, IN `reservationIdIN` INT)   BEGIN

END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `closeBetweenTwoDate` ()   BEGIN

END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `closeSingleDay` ()   BEGIN

END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `getReservationByDate` (IN `dateIN` DATE)   BEGIN
	SELECT 
    r.id,
    r.first_name,
    r.last_name,
    r.email,
    r.phone_number,
    r.comment, 
    r.reservation_type_id,
    r.user_id,
    r.payment_method_id,
    r.status_id,
    r.reserved_at,
    r.is_canceled,
    r.canceled_at,
    r.canceled_by
    FROM reservations r
    INNER JOIN reserved_hours rh 
    ON r.id = rh.reservation_id
    INNER JOIN reserved_dates rd 
    ON rh.date_id = rd.id
    WHERE rd.date = dateIN
    ;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `getReservationByUserId` (IN `userIdIN` INT)   BEGIN
	SELECT * FROM `reservations` WHERE reservations.user_id = userIdIN;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `getReservedDateByMonth` (IN `dateIN` DATE)   BEGIN
	SELECT * FROM reserved_dates WHERE
    Month(reserved_dates.date) = Month(dateIN);
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `getReservedHoursByDate` (IN `dateIN` DATE)   BEGIN
	SELECT 
	*
    FROM reserved_hours 
    INNER JOIN reserved_dates ON 
	reserved_hours.date_id = reserved_dates.id 
    AND DAY(reserved_dates.date) = DAY(dateIN);
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `login` (IN `usernameIN` VARCHAR(100), IN `passwordIN` VARCHAR(100))   BEGIN
	SELECT * FROM user WHERE user.username = usernameIN AND user.password = passwordIN;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `makeReservation` (IN `firstNameIN` VARCHAR(100), IN `lastNameIN` VARCHAR(100), IN `emailIN` VARCHAR(100), IN `phoneNumberIN` VARCHAR(100), IN `commentIN` TEXT, IN `reservationTypeIN` INT, IN `userIdIN` INT, IN `paymentMethodIN` INT, OUT `result` VARCHAR(100))   BEGIN
	INSERT INTO `reservations`(
        `first_name`, 
        `last_name`, 
        `email`, 
        `phone_number`, 
        `comment`, 
        `reservation_type_id`, 
        `user_id`, 
        `payment_method_id`) 
        VALUES (
        	firstNameIN,
        	lastNameIN,
            emailIN,
            phoneNumberIN,
            commentIN,
            reservationTypeIN,
            userIdIN,
			paymentMethodIN
        );
       
       	SET result = "successfully reservation";
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `register` (IN `usernameIN` VARCHAR(100), IN `emailIN` VARCHAR(100), IN `passwordIN` VARCHAR(100), OUT `result` VARCHAR(100))   BEGIN
	INSERT INTO `user`(`username`, `email`, `password`, `pfp_path`) VALUES (usernameIN, emailIN, passwordIN, "");
    SET result = "successfull registration";
END$$

DELIMITER ;

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `devices`
--

CREATE TABLE `devices` (
  `id` int(11) NOT NULL,
  `name` varchar(100) NOT NULL,
  `category_id` int(11) NOT NULL,
  `amount` int(2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- A tábla adatainak kiíratása `devices`
--

INSERT INTO `devices` (`id`, `name`, `category_id`, `amount`) VALUES
(3, 'mikrofon1', 1, 2),
(4, 'mikrofon2', 1, 2),
(5, 'gitar1', 2, 2),
(6, 'gitar2', 2, 2),
(7, 'mikrofon3', 1, 1),
(8, 'mikrofon4', 1, 1),
(9, 'mikrofon5', 1, 1),
(10, 'gitar3', 2, 1),
(11, 'gitar4', 2, 1),
(12, 'gitar5', 2, 1),
(13, 'erosito1', 3, 1),
(14, 'erosito2', 3, 1),
(15, 'erosito3', 3, 1),
(16, 'erosito4', 3, 1),
(17, 'erosito5', 3, 1),
(18, 'zongora1', 4, 1),
(19, 'zongora2', 4, 1),
(20, 'zongora3', 4, 1),
(21, 'zongora4', 4, 1),
(22, 'zongora5', 4, 1),
(23, 'dob1', 5, 1),
(24, 'dob2', 5, 1),
(25, 'dob3', 5, 1),
(26, 'dob4', 5, 1),
(27, 'dob5', 5, 1);

--
-- Eseményindítók `devices`
--
DELIMITER $$
CREATE TRIGGER `checkFullDay` BEFORE INSERT ON `devices` FOR EACH ROW BEGIN

END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `devices_category`
--

CREATE TABLE `devices_category` (
  `id` int(11) NOT NULL,
  `name` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- A tábla adatainak kiíratása `devices_category`
--

INSERT INTO `devices_category` (`id`, `name`) VALUES
(1, 'mikrofonok'),
(2, 'gitarok'),
(3, 'erositok'),
(4, 'zongorak'),
(5, 'dobok');

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `devices_reservation_type`
--

CREATE TABLE `devices_reservation_type` (
  `id` int(11) NOT NULL,
  `reservation_tpye_id` int(11) NOT NULL,
  `device_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `event_type`
--

CREATE TABLE `event_type` (
  `id` int(11) NOT NULL,
  `name` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

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
  `banner_img_path` longtext NOT NULL,
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
(1, 'Új hangszerek érkeztek a terembe', 'Megérkeztek a legújabb hangszereink, amelyeket bárki kipróbálhat próba közben. A dobkészletet teljesen felújítottuk, valamint új gitárerősítőket szereztünk be. Így még jobb hangzást tudunk biztosítani a zenekaroknak. Gyertek el és teszteljétek őket elsőként!', 'assets/images/news/placeholder.png', 1, 1, '2025-08-23 11:19:02', 0, NULL, NULL),
(2, 'Akciós próbadíjak szeptemberben', 'Ebben a hónapban kedvezményes áron bérelhetitek a próbatermet. A hétköznapi délutáni sávokra 20% kedvezményt biztosítunk. Ha rendszeresen jártok, még további engedményeket is adunk. Ne hagyjátok ki a lehetőséget!', 'assets/images/news/placeholder.png', 1, 2, '2025-08-23 11:19:02', 0, NULL, NULL),
(3, 'Nyílt nap a próbateremben', 'Szeretettel várunk minden érdeklődőt a nyílt napunkon. Lehetőségetek lesz kipróbálni a termet és a hangszereket teljesen ingyen. A program során bemutatjuk a felszerelést és válaszolunk minden kérdésre. Gyere el, és hozd magaddal zenész barátaidat is!', 'assets/images/news/placeholder.png', 1, 3, '2025-08-23 11:19:38', 0, NULL, NULL),
(4, 'Új foglalási rendszer indult', 'Mostantól egyszerűbben és gyorsabban tudtok időpontot foglalni. Az online naptár segítségével azonnal látható, mikor szabad a terem. Így elkerülhetők a félreértések és ütközések. Próbáljátok ki, és foglaljatok pár kattintással!', 'assets/images/news/placeholder.png', 1, 3, '2025-08-27 08:07:08', 0, NULL, NULL),
(5, 'Koncert a próbaterem zenekaraival', 'A nálunk próbáló zenekarok közül többen fellépnek egy közös koncerten. Az esemény célja, hogy bemutassuk a helyi tehetségeket. A belépés ingyenes, mindenkit szeretettel várunk. Részletek hamarosan a weboldalunkon!', 'assets/images/news/placeholder.png', 1, 5, '2025-08-27 08:07:08', 0, NULL, NULL);

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `payment_methods`
--

CREATE TABLE `payment_methods` (
  `id` int(11) NOT NULL,
  `name` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- A tábla adatainak kiíratása `payment_methods`
--

INSERT INTO `payment_methods` (`id`, `name`) VALUES
(1, 'Kártyás'),
(2, 'Készpénz'),
(3, 'Revolut');

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `reservations`
--

CREATE TABLE `reservations` (
  `id` int(11) NOT NULL,
  `first_name` varchar(100) NOT NULL,
  `last_name` varchar(100) NOT NULL,
  `email` varchar(100) NOT NULL,
  `phone_number` varchar(20) NOT NULL,
  `comment` longtext,
  `reservation_type_id` int(11) NOT NULL,
  `user_id` int(11) DEFAULT NULL,
  `payment_method_id` int(11) NOT NULL,
  `status_id` int(11) NOT NULL DEFAULT '1',
  `reserved_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `is_canceled` tinyint(1) NOT NULL DEFAULT '0',
  `canceled_at` datetime DEFAULT NULL,
  `canceled_by` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- A tábla adatainak kiíratása `reservations`
--

INSERT INTO `reservations` (`id`, `first_name`, `last_name`, `email`, `phone_number`, `comment`, `reservation_type_id`, `user_id`, `payment_method_id`, `status_id`, `reserved_at`, `is_canceled`, `canceled_at`, `canceled_by`) VALUES
(1, 'elso1', 'masodik1', 'elso@gmail.com', '123421', NULL, 1, 1, 2, 1, '2025-08-28 19:34:09', 0, '2025-08-28 19:33:10', NULL),
(2, 'elso2', 'masodik2', 'masodik@gmail.com', '31231231', 'asdasdasd', 1, 1, 1, 1, '2025-08-28 19:34:09', 0, '2025-08-28 19:33:10', NULL),
(3, 'elso3', 'masodik3', 'email3@gmail.com', '412412', 'asdasddas', 1, 2, 2, 1, '2025-08-28 19:35:08', 0, '2025-08-28 19:34:20', NULL),
(4, 'asd', 'ads', 'asd', 'ads', 'asd', 1, 1, 1, 1, '2025-09-02 10:54:04', 0, NULL, NULL),
(5, 'a', 'a', 'a', 'a', 'a', 1, 1, 1, 1, '2025-09-02 11:33:14', 0, NULL, NULL),
(6, 'a', 'aa', 'a', 'a', 'a', 1, 1, 1, 1, '2025-09-02 11:33:58', 0, NULL, NULL),
(7, 'a', 'a', 'a', 'a', 'a', 1, 1, 1, 1, '2025-09-02 18:20:02', 0, NULL, NULL),
(8, 'a', 'a', 'a', 'a', 'a', 1, 1, 1, 1, '2025-09-02 18:26:02', 0, NULL, NULL),
(9, 'a', 'a', 'a', 'a', 'a', 1, 1, 1, 1, '2025-09-02 18:27:51', 0, NULL, NULL);

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `reservation_type`
--

CREATE TABLE `reservation_type` (
  `id` int(11) NOT NULL,
  `name` varchar(100) NOT NULL,
  `price` int(6) NOT NULL,
  `amount` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- A tábla adatainak kiíratása `reservation_type`
--

INSERT INTO `reservation_type` (`id`, `name`, `price`, `amount`) VALUES
(1, 'Egyéni Gyakorlás', 2000, 'asd'),
(2, 'Zenekari Próba', 4000, 'asd');

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `reserved_dates`
--

CREATE TABLE `reserved_dates` (
  `id` int(11) NOT NULL,
  `date` date NOT NULL,
  `is_holiday` tinyint(1) NOT NULL DEFAULT '0',
  `is_closed` tinyint(1) NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- A tábla adatainak kiíratása `reserved_dates`
--

INSERT INTO `reserved_dates` (`id`, `date`, `is_holiday`, `is_closed`) VALUES
(5, '2025-09-04', 0, 0),
(6, '2025-09-02', 0, 0);

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `reserved_hours`
--

CREATE TABLE `reserved_hours` (
  `id` int(11) NOT NULL,
  `start` int(2) NOT NULL,
  `end` int(2) NOT NULL,
  `date_id` int(11) NOT NULL,
  `reservation_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- A tábla adatainak kiíratása `reserved_hours`
--

INSERT INTO `reserved_hours` (`id`, `start`, `end`, `date_id`, `reservation_id`) VALUES
(2, 10, 12, 5, 1),
(3, 10, 12, 6, 2),
(4, 14, 16, 5, 3);

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `review`
--

CREATE TABLE `review` (
  `id` int(11) NOT NULL,
  `author_id` int(11) NOT NULL,
  `review_text` longtext NOT NULL,
  `rating` double NOT NULL,
  `like_count` int(4) DEFAULT '0',
  `dislike_count` int(4) DEFAULT '0',
  `is_anonymus` tinyint(1) NOT NULL DEFAULT '0',
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- A tábla adatainak kiíratása `review`
--

INSERT INTO `review` (`id`, `author_id`, `review_text`, `rating`, `like_count`, `dislike_count`, `is_anonymus`, `created_at`) VALUES
(1, 1, 'asd', 1, 0, 0, 0, '2025-08-24 20:16:39'),
(4, 1, 'asd', 1, 0, 0, 0, '2025-08-27 10:25:08'),
(7, 1, 'asd', 1, 0, 0, 0, '2025-08-27 10:32:55'),
(8, 1, 'asd', 1, 0, 0, 0, '2025-08-28 20:22:46'),
(9, 1, 'asd', 1, 0, 0, 0, '2025-08-30 13:32:17'),
(10, 1, 'asd', 1, 0, 0, 0, '2025-08-30 15:50:05'),
(11, 1, 'asd', 1, 0, 0, 0, '2025-08-30 15:50:44'),
(12, 1, 'testASD', 2, 0, 0, 0, '2025-08-30 16:00:41'),
(13, 1, 'testASD', 2, 0, 0, 1, '2025-08-30 16:00:53'),
(14, 1, 'asdfasasdgdgsagdsgsdgdsagasdggdsagdsagdsa', 5, 0, 0, 0, '2025-08-30 16:07:44'),
(15, 1, 'dsadasdasd', 2.5, 0, 0, 0, '2025-08-30 16:10:25'),
(16, 1, 'dasdsadas', 2.5, 0, 0, 0, '2025-08-30 16:10:43'),
(17, 1, 'tasdfdasdasafssaffasfsafsaafsfasfasfas', 2.5, 0, 0, 0, '2025-08-30 16:20:18');

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `role`
--

CREATE TABLE `role` (
  `id` int(11) NOT NULL,
  `name` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- A tábla adatainak kiíratása `role`
--

INSERT INTO `role` (`id`, `name`) VALUES
(1, 'user'),
(2, 'admin'),
(3, 'super admin');

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `rules`
--

CREATE TABLE `rules` (
  `id` int(11) NOT NULL,
  `text` longtext NOT NULL,
  `last_edit_at` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- A tábla adatainak kiíratása `rules`
--

INSERT INTO `rules` (`id`, `text`, `last_edit_at`) VALUES
(1, 'text', NULL);

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `special_offer`
--

CREATE TABLE `special_offer` (
  `id` int(11) NOT NULL,
  `name` varchar(100) NOT NULL,
  `reservation_type_id` int(11) NOT NULL,
  `offer_amount` int(2) NOT NULL,
  `start_date` date NOT NULL,
  `end_date` date NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

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
(2, 'Befejezett');

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `unitofaccount`
--

CREATE TABLE `unitofaccount` (
  `id` int(11) NOT NULL,
  `name` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `user`
--

CREATE TABLE `user` (
  `id` int(11) NOT NULL,
  `username` varchar(100) NOT NULL,
  `email` varchar(100) NOT NULL,
  `password` varchar(64) NOT NULL,
  `pfp_path` longtext NOT NULL,
  `role_id` int(11) NOT NULL DEFAULT '1',
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `last_login` datetime DEFAULT NULL,
  `is_deleted` tinyint(1) DEFAULT NULL,
  `deleted_at` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- A tábla adatainak kiíratása `user`
--

INSERT INTO `user` (`id`, `username`, `email`, `password`, `pfp_path`, `role_id`, `created_at`, `last_login`, `is_deleted`, `deleted_at`) VALUES
(1, 'test', 'test', 'test', 'test', 1, '2025-08-23 04:45:44', NULL, 0, NULL),
(2, 'testAdmin', 'testAdmin', 'testAdmin', 'testAdmin', 2, '2025-08-23 04:50:02', NULL, 0, NULL),
(3, 'testSuperAdmin', 'testSuperAdmin', 'testSuperAdmin', 'testSuperAdmin', 3, '2025-08-23 04:50:02', NULL, 0, NULL),
(8, 'ads', 'email', 'password', '', 1, '2025-08-26 13:15:51', NULL, 0, NULL),
(9, 'ads', '1', 'password', '', 1, '2025-08-26 13:18:28', NULL, 0, NULL),
(10, 'ads', 'adads', 'password', '', 1, '2025-08-26 13:20:53', NULL, 0, NULL),
(11, 'asd', 'asd', 'asd', '', 1, '2025-08-26 15:33:35', NULL, 0, NULL),
(12, 'as', 'as', 'as', '', 1, '2025-09-02 10:55:08', NULL, NULL, NULL),
(13, 'a', 'a', 'a', '', 1, '2025-09-02 11:00:04', NULL, NULL, NULL),
(14, 'ads', 'adads', 'password', '', 1, '2025-09-02 11:19:30', NULL, NULL, NULL),
(15, 'ads', 'adads', 'password', '', 1, '2025-09-02 11:21:24', NULL, NULL, NULL),
(16, 'ads', 'adads', 'password', '', 1, '2025-09-02 11:30:26', NULL, NULL, NULL);

--
-- Indexek a kiírt táblákhoz
--

--
-- A tábla indexei `devices`
--
ALTER TABLE `devices`
  ADD PRIMARY KEY (`id`),
  ADD KEY `category` (`category_id`);

--
-- A tábla indexei `devices_category`
--
ALTER TABLE `devices_category`
  ADD PRIMARY KEY (`id`);

--
-- A tábla indexei `devices_reservation_type`
--
ALTER TABLE `devices_reservation_type`
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
-- A tábla indexei `payment_methods`
--
ALTER TABLE `payment_methods`
  ADD PRIMARY KEY (`id`);

--
-- A tábla indexei `reservations`
--
ALTER TABLE `reservations`
  ADD PRIMARY KEY (`id`),
  ADD KEY `user` (`user_id`),
  ADD KEY `reservation_type` (`reservation_type_id`),
  ADD KEY `cancelled` (`canceled_by`),
  ADD KEY `payment_method` (`payment_method_id`),
  ADD KEY `status` (`status_id`);

--
-- A tábla indexei `reservation_type`
--
ALTER TABLE `reservation_type`
  ADD PRIMARY KEY (`id`);

--
-- A tábla indexei `reserved_dates`
--
ALTER TABLE `reserved_dates`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `date` (`date`);

--
-- A tábla indexei `reserved_hours`
--
ALTER TABLE `reserved_hours`
  ADD PRIMARY KEY (`id`),
  ADD KEY `date` (`date_id`),
  ADD KEY `res` (`reservation_id`);

--
-- A tábla indexei `review`
--
ALTER TABLE `review`
  ADD PRIMARY KEY (`id`),
  ADD KEY `author` (`author_id`);

--
-- A tábla indexei `role`
--
ALTER TABLE `role`
  ADD PRIMARY KEY (`id`);

--
-- A tábla indexei `rules`
--
ALTER TABLE `rules`
  ADD PRIMARY KEY (`id`);

--
-- A tábla indexei `special_offer`
--
ALTER TABLE `special_offer`
  ADD PRIMARY KEY (`id`),
  ADD KEY `special_offer` (`reservation_type_id`);

--
-- A tábla indexei `status`
--
ALTER TABLE `status`
  ADD PRIMARY KEY (`id`);

--
-- A tábla indexei `unitofaccount`
--
ALTER TABLE `unitofaccount`
  ADD PRIMARY KEY (`id`);

--
-- A tábla indexei `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`),
  ADD KEY `role` (`role_id`);

--
-- A kiírt táblák AUTO_INCREMENT értéke
--

--
-- AUTO_INCREMENT a táblához `devices`
--
ALTER TABLE `devices`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=28;

--
-- AUTO_INCREMENT a táblához `devices_category`
--
ALTER TABLE `devices_category`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT a táblához `devices_reservation_type`
--
ALTER TABLE `devices_reservation_type`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT a táblához `event_type`
--
ALTER TABLE `event_type`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

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
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT a táblához `payment_methods`
--
ALTER TABLE `payment_methods`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT a táblához `reservations`
--
ALTER TABLE `reservations`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT a táblához `reservation_type`
--
ALTER TABLE `reservation_type`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT a táblához `reserved_dates`
--
ALTER TABLE `reserved_dates`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT a táblához `reserved_hours`
--
ALTER TABLE `reserved_hours`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT a táblához `review`
--
ALTER TABLE `review`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=18;

--
-- AUTO_INCREMENT a táblához `role`
--
ALTER TABLE `role`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT a táblához `rules`
--
ALTER TABLE `rules`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT a táblához `special_offer`
--
ALTER TABLE `special_offer`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT a táblához `status`
--
ALTER TABLE `status`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT a táblához `unitofaccount`
--
ALTER TABLE `unitofaccount`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT a táblához `user`
--
ALTER TABLE `user`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=17;

--
-- Megkötések a kiírt táblákhoz
--

--
-- Megkötések a táblához `devices`
--
ALTER TABLE `devices`
  ADD CONSTRAINT `category` FOREIGN KEY (`category_id`) REFERENCES `devices_category` (`id`);

--
-- Megkötések a táblához `devices_reservation_type`
--
ALTER TABLE `devices_reservation_type`
  ADD CONSTRAINT `device` FOREIGN KEY (`id`) REFERENCES `devices` (`id`),
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
-- Megkötések a táblához `reservations`
--
ALTER TABLE `reservations`
  ADD CONSTRAINT `cancelled` FOREIGN KEY (`canceled_by`) REFERENCES `user` (`id`),
  ADD CONSTRAINT `payment_method` FOREIGN KEY (`payment_method_id`) REFERENCES `payment_methods` (`id`),
  ADD CONSTRAINT `reservation_type` FOREIGN KEY (`reservation_type_id`) REFERENCES `reservation_type` (`id`),
  ADD CONSTRAINT `status` FOREIGN KEY (`status_id`) REFERENCES `status` (`id`),
  ADD CONSTRAINT `user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`);

--
-- Megkötések a táblához `reserved_hours`
--
ALTER TABLE `reserved_hours`
  ADD CONSTRAINT `date` FOREIGN KEY (`date_id`) REFERENCES `reserved_dates` (`id`),
  ADD CONSTRAINT `res` FOREIGN KEY (`reservation_id`) REFERENCES `reservations` (`id`);

--
-- Megkötések a táblához `review`
--
ALTER TABLE `review`
  ADD CONSTRAINT `author` FOREIGN KEY (`author_id`) REFERENCES `user` (`id`);

--
-- Megkötések a táblához `special_offer`
--
ALTER TABLE `special_offer`
  ADD CONSTRAINT `special_offer` FOREIGN KEY (`reservation_type_id`) REFERENCES `reservation_type` (`id`);

--
-- Megkötések a táblához `user`
--
ALTER TABLE `user`
  ADD CONSTRAINT `role` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
