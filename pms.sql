-- phpMyAdmin SQL Dump
-- version 5.1.2
-- https://www.phpmyadmin.net/
--
-- Gép: localhost:3306
-- Létrehozás ideje: 2025. Aug 27. 12:16
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
CREATE DEFINER=`root`@`localhost` PROCEDURE `addReview` (IN `userIdIN` INT, IN `reviewTextIN` LONGTEXT, IN `ratingIN` DOUBLE)   BEGIN
	INSERT INTO `review`(`author_id`, `review_text`, `rating`) VALUES (userIdIN, reviewTextIN, ratingIN);
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `cancelReservation` (IN `userIdIN` INT, IN `reservationIdIN` INT)   BEGIN

END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `getReservationByUserId` (IN `userIdIN` INT)   BEGIN
	SELECT * FROM `reservations` WHERE reservations.user_id = userIdIN;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `login` (IN `usernameIN` VARCHAR(100), IN `passwordIN` VARCHAR(100))   BEGIN
	SELECT * FROM user WHERE user.username = usernameIN AND user.password = passwordIN;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `register` (IN `usernameIN` VARCHAR(100), IN `emailIN` VARCHAR(100), IN `passwordIN` VARCHAR(100))   BEGIN
	INSERT INTO `user`(`username`, `email`, `password`, `pfp_path`) VALUES (usernameIN, emailIN, passwordIN, "");
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
(6, 'gitar2', 2, 2);

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
(2, 'gitarok');

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
(1, 'kep1', 'kep1', 1),
(2, 'kep2', 'kep2', 2);

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
(1, 'hir1', 'Lorem ipsum dolor sit amet consectetur. Risus aliquet ipsum ultrices mattis consequat in. Diam suspendisse etiam lorem orci lobortis risus nibh cras tincidunt. Sed aenean faucibus libero amet. Donec gravida aliquam nulla elementum sed fusce posuere viverra in.', 'asd', 1, 1, '2025-08-23 11:19:02', 0, NULL, NULL),
(2, 'hir2', 'Lorem ipsum dolor sit amet consectetur. Risus aliquet ipsum ultrices mattis consequat in. Diam suspendisse etiam lorem orci lobortis risus nibh cras tincidunt. Sed aenean faucibus libero amet. Donec gravida aliquam nulla elementum sed fusce posuere viverra in.', 'asd', 1, 2, '2025-08-23 11:19:02', 0, NULL, NULL),
(3, 'hir3', 'Lorem ipsum dolor sit amet consectetur. Risus aliquet ipsum ultrices mattis consequat in. Diam suspendisse etiam lorem orci lobortis risus nibh cras tincidunt. Sed aenean faucibus libero amet. Donec gravida aliquam nulla elementum sed fusce posuere viverra in.', 'asd', 1, 3, '2025-08-23 11:19:38', 0, NULL, NULL),
(4, 'Hír 4', 'Lorem ipsum dolor sit amet consectetur. Risus aliquet ipsum ultrices mattis consequat in. Diam suspendisse etiam lorem orci lobortis risus nibh cras tincidunt. Sed aenean faucibus libero amet. Donec gravida aliquam nulla elementum sed fusce posuere viverra in.', 'asd', 1, 3, '2025-08-27 08:07:08', 0, NULL, NULL),
(5, 'Hír 5', 'Lorem ipsum dolor sit amet consectetur. Risus aliquet ipsum ultrices mattis consequat in. Diam suspendisse etiam lorem orci lobortis risus nibh cras tincidunt. Sed aenean faucibus libero amet. Donec gravida aliquam nulla elementum sed fusce posuere viverra in.', 'asd', 1, 5, '2025-08-27 08:07:08', 0, NULL, NULL);

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
  `reserved_date_id` int(11) NOT NULL,
  `payment_method_id` int(11) NOT NULL,
  `status_id` int(11) NOT NULL,
  `reserved_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `is_canceled` tinyint(1) NOT NULL DEFAULT '0',
  `canceled_at` datetime NOT NULL,
  `canceled_by` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

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
  `start_hour` int(2) NOT NULL,
  `end_hour` int(2) NOT NULL,
  `is_closed` tinyint(1) NOT NULL DEFAULT '0'
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
(7, 1, 'asd', 1, 0, 0, 0, '2025-08-27 10:32:55');

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
(11, 'asd', 'asd', 'asd', '', 1, '2025-08-26 15:33:35', NULL, 0, NULL);

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
  ADD KEY `reserved_date` (`reserved_date_id`),
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
  ADD PRIMARY KEY (`id`);

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
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT a táblához `devices_category`
--
ALTER TABLE `devices_category`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

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
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

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
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT a táblához `reservation_type`
--
ALTER TABLE `reservation_type`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT a táblához `reserved_dates`
--
ALTER TABLE `reserved_dates`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT a táblához `review`
--
ALTER TABLE `review`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

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
-- AUTO_INCREMENT a táblához `user`
--
ALTER TABLE `user`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

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
  ADD CONSTRAINT `reserved_date` FOREIGN KEY (`reserved_date_id`) REFERENCES `reserved_dates` (`id`),
  ADD CONSTRAINT `status` FOREIGN KEY (`status_id`) REFERENCES `status` (`id`),
  ADD CONSTRAINT `user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`);

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
