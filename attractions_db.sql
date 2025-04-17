-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1:3306
-- Généré le : jeu. 17 avr. 2025 à 16:54
-- Version du serveur : 9.1.0
-- Version de PHP : 8.3.14

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `attractions_db`
--

-- --------------------------------------------------------

--
-- Structure de la table `account`
--

DROP TABLE IF EXISTS `account`;
CREATE TABLE IF NOT EXISTS `account` (
  `account_id` int NOT NULL AUTO_INCREMENT,
  `firstname` varchar(50) NOT NULL,
  `surname` varchar(50) NOT NULL,
  `birthdate` date NOT NULL,
  `age` smallint NOT NULL,
  `email` varchar(100) NOT NULL,
  `password` varchar(255) NOT NULL,
  `account_type` int NOT NULL,
  PRIMARY KEY (`account_id`),
  UNIQUE KEY `email` (`email`)
) ;

--
-- Déchargement des données de la table `account`
--

INSERT INTO `account` (`account_id`, `firstname`, `surname`, `birthdate`, `age`, `email`, `password`, `account_type`) VALUES
(2, 'William', 'Walker', '2004-09-29', 20, 'w@mail', 'w', 1),
(5, 'Wi', 'Walker', '2004-09-29', 20, 'wi@mail', 'wi', 2);

-- --------------------------------------------------------

--
-- Structure de la table `attraction`
--

DROP TABLE IF EXISTS `attraction`;
CREATE TABLE IF NOT EXISTS `attraction` (
  `attraction_id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL,
  `description` varchar(500) DEFAULT NULL,
  `person_type` varchar(50) DEFAULT NULL,
  `image_path` varchar(100) DEFAULT NULL,
  `price` decimal(10,3) DEFAULT NULL,
  PRIMARY KEY (`attraction_id`)
) ENGINE=MyISAM AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `attraction`
--

INSERT INTO `attraction` (`attraction_id`, `name`, `description`, `person_type`, `image_path`, `price`) VALUES
(1, 'Belenos', 'Belenos, inspiré de l\'attraction Toutatis, offre des sensations fortes dans un décor mythique. Montez à bord pour une expérience palpitante où virages serrés et accélérations impressionnantes vous attendent. Taille minimum requise : 1.30 m.', 'Sensations fortes', 'belenos.png', 4.250),
(2, 'Dragon', 'Dragon, connu sous le nom de \"dragon du jardin d\'acclimatation\", vous transporte dans un univers féerique. Vivez une aventure où le mythe rencontre la réalité. Taille minimum requise : 1.20 m.', 'Découverte', 'dragon.png', 4.250),
(3, 'Le Carroussel', 'Le Carroussel des Héros est une attraction emblématique pour toute la famille. Embarquez pour une balade enchanteresse dans un univers de légendes, sans exigence de taille pour les petits accompagnés.', 'Famille', 'carroussel.png', 3.000),
(4, 'Meduse Express', 'Meduse Express, équivalent de Pégase Express, combine légèreté et vitesse pour une aventure divertissante. Une attraction idéale pour découvrir les sensations en douceur. Taille minimum requise : 1.10 m.', 'Famille', 'meduseExpress.png', 3.000),
(5, 'Par Isis', 'Par Isis, inspiré d\'Oziris, propose un voyage mythologique au cœur de mystères et d\'effets visuels époustouflants. L\'attraction est conçue pour offrir une montée d\'adrénaline mesurée. Taille minimum requise : 1.25 m.', 'Sensations fortes', 'parisis.png', 6.500),
(6, 'Zeus', 'Zeus, reprenant les codes de Tonnerre 2 Zeus, vous promet une expérience électrisante avec des accélérations fulgurantes et des descentes vertigineuses. Idéal pour les amateurs de sensations intenses. Taille minimum requise : 1.35 m.', 'Sensations fortes', 'zeus.png', 6.500);

-- --------------------------------------------------------

--
-- Structure de la table `orders`
--

DROP TABLE IF EXISTS `orders`;
CREATE TABLE IF NOT EXISTS `orders` (
  `order_id` int NOT NULL AUTO_INCREMENT,
  `rdv_fulltime` datetime DEFAULT NULL,
  `person_count` int DEFAULT NULL,
  `price` decimal(8,2) DEFAULT NULL,
  `status` enum('Paid','Pending','Cancelled') DEFAULT NULL,
  `attraction_id` int DEFAULT NULL,
  `reservation_id` int DEFAULT NULL,
  `payer_name` varchar(100) DEFAULT NULL,
  `payer_email` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`order_id`),
  KEY `attraction_id` (`attraction_id`),
  KEY `reservation_id` (`reservation_id`)
) ENGINE=MyISAM AUTO_INCREMENT=34 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `orders`
--

INSERT INTO `orders` (`order_id`, `rdv_fulltime`, `person_count`, `price`, `status`, `attraction_id`, `reservation_id`, `payer_name`, `payer_email`) VALUES
(7, '2025-04-13 22:16:59', 2, 15.50, 'Paid', 1, 42, NULL, NULL),
(6, '2025-04-13 22:16:31', 2, 35.50, 'Paid', 2, 42, NULL, NULL),
(8, '2025-04-13 22:29:18', 2, 8.00, 'Pending', 1, 42, NULL, NULL),
(9, '2025-04-13 22:33:04', 2, 35.50, 'Paid', 1, 42, NULL, NULL),
(10, '2025-04-13 22:33:14', 2, 8.00, 'Pending', 1, 42, NULL, NULL),
(11, '2025-04-13 22:33:22', 2, 15.50, 'Pending', 1, 42, NULL, NULL),
(12, '2025-04-13 22:36:19', 2, 7.50, 'Paid', 1, 42, NULL, NULL),
(13, '2025-04-13 22:38:23', 2, 35.50, 'Pending', 1, 42, NULL, NULL),
(14, '2025-04-13 22:39:54', 2, 35.50, 'Paid', 1, 42, NULL, NULL),
(15, '2025-04-13 22:40:27', 2, 35.50, 'Paid', 1, 42, NULL, NULL),
(16, '2025-04-15 19:47:15', 2, 7.50, 'Paid', 1, 42, NULL, NULL),
(17, '2025-04-15 19:47:52', 2, 7.50, 'Paid', 1, 42, NULL, NULL),
(18, '2025-04-15 19:49:40', 2, 7.50, 'Paid', 1, 42, NULL, NULL),
(30, '2025-04-17 14:40:09', 5, 94.50, 'Paid', 5, 5, NULL, NULL),
(33, '2025-04-17 15:41:49', 6, 111.00, 'Paid', 4, 8, NULL, NULL);

-- --------------------------------------------------------

--
-- Structure de la table `program`
--

DROP TABLE IF EXISTS `program`;
CREATE TABLE IF NOT EXISTS `program` (
  `program_id` int NOT NULL AUTO_INCREMENT,
  `program_date` date DEFAULT NULL,
  `is_highSeason` tinyint(1) DEFAULT NULL,
  `special_day` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`program_id`)
) ENGINE=MyISAM AUTO_INCREMENT=276 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `program`
--

INSERT INTO `program` (`program_id`, `program_date`, `is_highSeason`, `special_day`) VALUES
(1, '2025-04-01', 0, '0'),
(2, '2025-04-02', 0, '0'),
(3, '2025-04-03', 0, '0'),
(4, '2025-04-04', 0, '0'),
(5, '2025-04-05', 1, '0'),
(6, '2025-04-06', 1, '0'),
(7, '2025-04-07', 0, '0'),
(8, '2025-04-08', 0, '0'),
(9, '2025-04-09', 0, '0'),
(10, '2025-04-10', 0, '0'),
(11, '2025-04-11', 0, '0'),
(12, '2025-04-12', 1, '0'),
(13, '2025-04-13', 1, '0'),
(14, '2025-04-14', 0, '0'),
(15, '2025-04-15', 0, '0'),
(16, '2025-04-16', 0, '0'),
(17, '2025-04-17', 0, '0'),
(18, '2025-04-18', 0, '1'),
(19, '2025-04-19', 1, '0'),
(20, '2025-04-20', 1, '0'),
(21, '2025-04-21', 0, '0'),
(22, '2025-04-22', 0, '0'),
(23, '2025-04-23', 0, '0'),
(24, '2025-04-24', 0, '0'),
(25, '2025-04-25', 0, '0'),
(26, '2025-04-26', 0, '0'),
(27, '2025-04-27', 1, '0'),
(28, '2025-04-28', 0, '0'),
(29, '2025-04-29', 0, '0'),
(30, '2025-04-30', 0, '0'),
(31, '2025-05-01', 0, '1'),
(32, '2025-05-02', 0, '0'),
(33, '2025-05-03', 1, '0'),
(34, '2025-05-04', 1, '0'),
(35, '2025-05-05', 0, '0'),
(36, '2025-05-06', 0, '0'),
(37, '2025-05-07', 0, '0'),
(38, '2025-05-08', 0, '1'),
(39, '2025-05-09', 0, '0'),
(40, '2025-05-10', 1, '0'),
(41, '2025-05-11', 1, '0'),
(42, '2025-05-12', 0, '0'),
(43, '2025-05-13', 0, '0'),
(44, '2025-05-14', 0, '0'),
(45, '2025-05-15', 0, '0'),
(46, '2025-05-16', 0, '0'),
(47, '2025-05-17', 1, '0'),
(48, '2025-05-18', 1, '0'),
(49, '2025-05-19', 0, '0'),
(50, '2025-05-20', 0, '0'),
(51, '2025-05-21', 0, '0'),
(52, '2025-05-22', 0, '0'),
(53, '2025-05-23', 0, '0'),
(54, '2025-05-24', 1, '0'),
(55, '2025-05-25', 1, '0'),
(56, '2025-05-26', 0, '0'),
(57, '2025-05-27', 0, '0'),
(58, '2025-05-28', 0, '0'),
(59, '2025-05-29', 0, '0'),
(60, '2025-05-30', 0, '0'),
(61, '2025-05-31', 1, '0'),
(62, '2025-06-01', 1, '0'),
(63, '2025-06-02', 0, '0'),
(64, '2025-06-03', 0, '0'),
(65, '2025-06-04', 0, '0'),
(66, '2025-06-05', 0, '0'),
(67, '2025-06-06', 0, '0'),
(68, '2025-06-07', 1, '0'),
(69, '2025-06-08', 1, '0'),
(70, '2025-06-09', 0, '0'),
(71, '2025-06-10', 0, '0'),
(72, '2025-06-11', 0, '0'),
(73, '2025-06-12', 0, '0'),
(74, '2025-06-13', 0, '0'),
(75, '2025-06-14', 1, '0'),
(76, '2025-06-15', 1, '0'),
(77, '2025-06-16', 0, '0'),
(78, '2025-06-17', 0, '0'),
(79, '2025-06-18', 0, '0'),
(80, '2025-06-19', 0, '0'),
(81, '2025-06-20', 0, '0'),
(82, '2025-06-21', 1, '0'),
(83, '2025-06-22', 1, '0'),
(84, '2025-06-23', 0, '0'),
(85, '2025-06-24', 0, '0'),
(86, '2025-06-25', 0, '0'),
(87, '2025-06-26', 0, '0'),
(88, '2025-06-27', 0, '0'),
(89, '2025-06-28', 1, '0'),
(90, '2025-06-29', 1, '0'),
(91, '2025-06-30', 0, '0'),
(92, '2025-07-01', 0, '0'),
(93, '2025-07-02', 0, '0'),
(94, '2025-07-03', 0, '0'),
(95, '2025-07-04', 0, '0'),
(96, '2025-07-05', 1, '0'),
(97, '2025-07-06', 1, '0'),
(98, '2025-07-07', 0, '0'),
(99, '2025-07-08', 0, '0'),
(100, '2025-07-09', 0, '0'),
(101, '2025-07-10', 0, '0'),
(102, '2025-07-11', 0, '0'),
(103, '2025-07-12', 1, '0'),
(104, '2025-07-13', 1, '0'),
(105, '2025-07-14', 0, '1'),
(106, '2025-07-15', 0, '0'),
(107, '2025-07-16', 0, '0'),
(108, '2025-07-17', 0, '0'),
(109, '2025-07-18', 0, '0'),
(110, '2025-07-19', 1, '0'),
(111, '2025-07-20', 1, '0'),
(112, '2025-07-21', 0, '0'),
(113, '2025-07-22', 0, '0'),
(114, '2025-07-23', 0, '0'),
(115, '2025-07-24', 0, '0'),
(116, '2025-07-25', 0, '0'),
(117, '2025-07-26', 1, '0'),
(118, '2025-07-27', 1, '0'),
(119, '2025-07-28', 0, '0'),
(120, '2025-07-29', 0, '0'),
(121, '2025-07-30', 0, '0'),
(122, '2025-07-31', 0, '0'),
(123, '2025-08-01', 0, '0'),
(124, '2025-08-02', 1, '0'),
(125, '2025-08-03', 1, '0'),
(126, '2025-08-04', 0, '0'),
(127, '2025-08-05', 0, '0'),
(128, '2025-08-06', 0, '0'),
(129, '2025-08-07', 0, '0'),
(130, '2025-08-08', 0, '0'),
(131, '2025-08-09', 1, '0'),
(132, '2025-08-10', 1, '0'),
(133, '2025-08-11', 0, '0'),
(134, '2025-08-12', 0, '0'),
(135, '2025-08-13', 0, '0'),
(136, '2025-08-14', 0, '0'),
(137, '2025-08-15', 0, '1'),
(138, '2025-08-16', 1, '0'),
(139, '2025-08-17', 1, '0'),
(140, '2025-08-18', 0, '0'),
(141, '2025-08-19', 0, '0'),
(142, '2025-08-20', 0, '0'),
(143, '2025-08-21', 0, '0'),
(144, '2025-08-22', 0, '0'),
(145, '2025-08-23', 1, '0'),
(146, '2025-08-24', 1, '0'),
(147, '2025-08-25', 0, '0'),
(148, '2025-08-26', 0, '0'),
(149, '2025-08-27', 0, '0'),
(150, '2025-08-28', 0, '0'),
(151, '2025-08-29', 0, '0'),
(152, '2025-08-30', 1, '0'),
(153, '2025-08-31', 1, '0'),
(154, '2025-09-01', 0, '0'),
(155, '2025-09-02', 0, '0'),
(156, '2025-09-03', 0, '0'),
(157, '2025-09-04', 0, '0'),
(158, '2025-09-05', 0, '0'),
(159, '2025-09-06', 1, '0'),
(160, '2025-09-07', 1, '0'),
(161, '2025-09-08', 0, '0'),
(162, '2025-09-09', 0, '0'),
(163, '2025-09-10', 0, '0'),
(164, '2025-09-11', 0, '0'),
(165, '2025-09-12', 0, '0'),
(166, '2025-09-13', 1, '0'),
(167, '2025-09-14', 1, '0'),
(168, '2025-09-15', 0, '0'),
(169, '2025-09-16', 0, '0'),
(170, '2025-09-17', 0, '0'),
(171, '2025-09-18', 0, '0'),
(172, '2025-09-19', 0, '0'),
(173, '2025-09-20', 1, '0'),
(174, '2025-09-21', 1, '0'),
(175, '2025-09-22', 0, '0'),
(176, '2025-09-23', 0, '0'),
(177, '2025-09-24', 0, '0'),
(178, '2025-09-25', 0, '0'),
(179, '2025-09-26', 0, '0'),
(180, '2025-09-27', 1, '0'),
(181, '2025-09-28', 1, '0'),
(182, '2025-09-29', 0, '0'),
(183, '2025-09-30', 0, '0'),
(184, '2025-10-01', 0, '0'),
(185, '2025-10-02', 0, '0'),
(186, '2025-10-03', 0, '0'),
(187, '2025-10-04', 1, '0'),
(188, '2025-10-05', 1, '0'),
(189, '2025-10-06', 0, '0'),
(190, '2025-10-07', 0, '0'),
(191, '2025-10-08', 0, '0'),
(192, '2025-10-09', 0, '0'),
(193, '2025-10-10', 0, '0'),
(194, '2025-10-11', 1, '0'),
(195, '2025-10-12', 1, '0'),
(196, '2025-10-13', 0, '0'),
(197, '2025-10-14', 0, '0'),
(198, '2025-10-15', 0, '0'),
(199, '2025-10-16', 0, '0'),
(200, '2025-10-17', 0, '0'),
(201, '2025-10-18', 1, '0'),
(202, '2025-10-19', 1, '0'),
(203, '2025-10-20', 0, '0'),
(204, '2025-10-21', 0, '0'),
(205, '2025-10-22', 0, '0'),
(206, '2025-10-23', 0, '0'),
(207, '2025-10-24', 0, '0'),
(208, '2025-10-25', 1, '0'),
(209, '2025-10-26', 1, '0'),
(210, '2025-10-27', 0, '0'),
(211, '2025-10-28', 0, '0'),
(212, '2025-10-29', 0, '0'),
(213, '2025-10-30', 0, '0'),
(214, '2025-10-31', 0, '0'),
(215, '2025-11-01', 1, '1'),
(216, '2025-11-02', 1, '0'),
(217, '2025-11-03', 0, '0'),
(218, '2025-11-04', 0, '0'),
(219, '2025-11-05', 0, '0'),
(220, '2025-11-06', 0, '0'),
(221, '2025-11-07', 0, '0'),
(222, '2025-11-08', 1, '0'),
(223, '2025-11-09', 1, '0'),
(224, '2025-11-10', 0, '0'),
(225, '2025-11-11', 0, '1'),
(226, '2025-11-12', 0, '0'),
(227, '2025-11-13', 0, '0'),
(228, '2025-11-14', 0, '0'),
(229, '2025-11-15', 1, '0'),
(230, '2025-11-16', 1, '0'),
(231, '2025-11-17', 0, '0'),
(232, '2025-11-18', 0, '0'),
(233, '2025-11-19', 0, '0'),
(234, '2025-11-20', 0, '0'),
(235, '2025-11-21', 0, '0'),
(236, '2025-11-22', 1, '0'),
(237, '2025-11-23', 1, '0'),
(238, '2025-11-24', 0, '0'),
(239, '2025-11-25', 0, '0'),
(240, '2025-11-26', 0, '0'),
(241, '2025-11-27', 0, '0'),
(242, '2025-11-28', 0, '0'),
(243, '2025-11-29', 1, '0'),
(244, '2025-11-30', 1, '0'),
(245, '2025-12-01', 0, '0'),
(246, '2025-12-02', 0, '0'),
(247, '2025-12-03', 0, '0'),
(248, '2025-12-04', 0, '0'),
(249, '2025-12-05', 0, '0'),
(250, '2025-12-06', 1, '0'),
(251, '2025-12-07', 1, '0'),
(252, '2025-12-08', 0, '0'),
(253, '2025-12-09', 0, '0'),
(254, '2025-12-10', 0, '0'),
(255, '2025-12-11', 0, '0'),
(256, '2025-12-12', 0, '0'),
(257, '2025-12-13', 1, '0'),
(258, '2025-12-14', 1, '0'),
(259, '2025-12-15', 0, '0'),
(260, '2025-12-16', 0, '0'),
(261, '2025-12-17', 0, '0'),
(262, '2025-12-18', 0, '0'),
(263, '2025-12-19', 0, '0'),
(264, '2025-12-20', 1, '0'),
(265, '2025-12-21', 1, '0'),
(266, '2025-12-22', 0, '0'),
(267, '2025-12-23', 0, '0'),
(268, '2025-12-24', 0, '0'),
(269, '2025-12-25', 0, '1'),
(270, '2025-12-26', 0, '0'),
(271, '2025-12-27', 1, '0'),
(272, '2025-12-28', 1, '0'),
(273, '2025-12-29', 0, '0'),
(274, '2025-12-30', 0, '0'),
(275, '2025-12-31', 0, '0');

-- --------------------------------------------------------

--
-- Structure de la table `reservation`
--

DROP TABLE IF EXISTS `reservation`;
CREATE TABLE IF NOT EXISTS `reservation` (
  `reservation_id` int NOT NULL AUTO_INCREMENT,
  `adult_count` int DEFAULT NULL,
  `children_count` int DEFAULT NULL,
  `baby_count` int DEFAULT NULL,
  `dateReservation` date DEFAULT NULL,
  `program_id` int DEFAULT NULL,
  `account_id` int DEFAULT NULL,
  PRIMARY KEY (`reservation_id`),
  KEY `program_id` (`program_id`),
  KEY `account_id` (`account_id`)
) ENGINE=MyISAM AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `reservation`
--

INSERT INTO `reservation` (`reservation_id`, `adult_count`, `children_count`, `baby_count`, `dateReservation`, `program_id`, `account_id`) VALUES
(5, 3, 2, 2, '2025-04-22', 1, 2),
(8, 4, 2, 5, '2025-04-30', 1, 5);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
