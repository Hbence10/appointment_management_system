-- phpMyAdmin SQL Dump
-- version 5.1.2
-- https://www.phpmyadmin.net/
--
-- Gép: localhost:3306
-- Létrehozás ideje: 2025. Aug 22. 18:19
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
CREATE DEFINER=`root`@`localhost` PROCEDURE `add_devices` ()   BEGIN

END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `add_devices_category` ()   BEGIN

END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `add_dislike` ()   BEGIN

END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `add_like` ()   BEGIN

END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `add_news` ()   BEGIN

END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `add_reservation_type` ()   BEGIN

END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `add_special_offer` ()   BEGIN

END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `cancel_reservation` ()   BEGIN

END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `change_password` ()   BEGIN

END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `delete_devices` ()   BEGIN

END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `delete_devices_category` ()   BEGIN

END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `delete_news` ()   BEGIN

END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `delete_reservation_type` ()   BEGIN

END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `get_devices` ()   BEGIN

END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `get_devices_category` ()   BEGIN

END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `get_gallery_photos` ()   BEGIN

END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `get_reservation_by_day` ()   BEGIN

END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `get_reservation_by_user_id` ()   BEGIN

END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `get_reservation_types` ()   BEGIN

END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `get_reviews` ()   BEGIN

END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `get_rules` ()   BEGIN

END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `login` ()   BEGIN

END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `log_out` ()   BEGIN

END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `make_reservation` ()   BEGIN

END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `profile_delete` ()   BEGIN

END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `profil_update` ()   BEGIN

END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `registration` ()   BEGIN

END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `update_devices` ()   BEGIN

END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `update_devices_category` ()   BEGIN

END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `update_gallery_photo` ()   BEGIN

END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `update_news` ()   BEGIN

END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `update_reservation_type` ()   BEGIN

END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `update_rules` ()   BEGIN

END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `write_review` ()   BEGIN

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

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `devices_category`
--

CREATE TABLE `devices_category` (
  `id` int(11) NOT NULL,
  `name` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

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
-- Tábla szerkezet ehhez a táblához `gallery`
--

CREATE TABLE `gallery` (
  `id` int(11) NOT NULL,
  `photo_name` longtext NOT NULL,
  `photo_path` longtext NOT NULL,
  `placement` int(2) NOT NULL
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

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `paymenth_methods`
--

CREATE TABLE `paymenth_methods` (
  `id` int(11) NOT NULL,
  `name` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `reservation`
--

CREATE TABLE `reservation` (
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

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `reserved_dates`
--

CREATE TABLE `reserved_dates` (
  `id` int(11) NOT NULL,
  `start_time` datetime NOT NULL,
  `end_time` datetime NOT NULL,
  `is_closed` tinyint(1) NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `review`
--

CREATE TABLE `review` (
  `id` int(11) NOT NULL,
  `author_id` int(11) DEFAULT NULL,
  `review_text` longtext NOT NULL,
  `rating` double NOT NULL,
  `like_count` int(4) DEFAULT '0',
  `dislike_count` int(4) DEFAULT '0',
  `is_anonymus` tinyint(1) NOT NULL DEFAULT '0',
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `role`
--

CREATE TABLE `role` (
  `id` int(11) NOT NULL,
  `name` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `rules`
--

CREATE TABLE `rules` (
  `id` int(11) NOT NULL,
  `text` longtext NOT NULL,
  `last_edit_at` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

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
  `role_id` int(11) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `last_login` datetime DEFAULT NULL,
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0',
  `deleted_at` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

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
-- A tábla indexei `news`
--
ALTER TABLE `news`
  ADD PRIMARY KEY (`id`),
  ADD KEY `writer` (`writer_id`);

--
-- A tábla indexei `paymenth_methods`
--
ALTER TABLE `paymenth_methods`
  ADD PRIMARY KEY (`id`);

--
-- A tábla indexei `reservation`
--
ALTER TABLE `reservation`
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
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT a táblához `devices_category`
--
ALTER TABLE `devices_category`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT a táblához `devices_reservation_type`
--
ALTER TABLE `devices_reservation_type`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT a táblához `news`
--
ALTER TABLE `news`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT a táblához `paymenth_methods`
--
ALTER TABLE `paymenth_methods`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT a táblához `reservation`
--
ALTER TABLE `reservation`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT a táblához `reservation_type`
--
ALTER TABLE `reservation_type`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT a táblához `reserved_dates`
--
ALTER TABLE `reserved_dates`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT a táblához `review`
--
ALTER TABLE `review`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT a táblához `role`
--
ALTER TABLE `role`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT a táblához `rules`
--
ALTER TABLE `rules`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT a táblához `special_offer`
--
ALTER TABLE `special_offer`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT a táblához `status`
--
ALTER TABLE `status`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT a táblához `user`
--
ALTER TABLE `user`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

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
-- Megkötések a táblához `news`
--
ALTER TABLE `news`
  ADD CONSTRAINT `writer` FOREIGN KEY (`writer_id`) REFERENCES `user` (`id`);

--
-- Megkötések a táblához `reservation`
--
ALTER TABLE `reservation`
  ADD CONSTRAINT `cancelled` FOREIGN KEY (`canceled_by`) REFERENCES `user` (`id`),
  ADD CONSTRAINT `payment_method` FOREIGN KEY (`payment_method_id`) REFERENCES `paymenth_methods` (`id`),
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
