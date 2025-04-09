-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1:3306
-- Généré le : mar. 08 avr. 2025 à 16:08
-- Version du serveur : 8.0.35
-- Version de PHP : 8.2.13

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `parcattraction`
--

-- --------------------------------------------------------

--
-- Structure de la table `attraction`
--

DROP TABLE IF EXISTS `attraction`;
CREATE TABLE IF NOT EXISTS `attraction` (
  `ID_Attraction` int NOT NULL AUTO_INCREMENT,
  `Nom` varchar(100) COLLATE utf8mb3_swedish_ci NOT NULL,
  `Description` text COLLATE utf8mb3_swedish_ci,
  `Type_Pers` varchar(100) COLLATE utf8mb3_swedish_ci DEFAULT NULL,
  `Video` varchar(255) COLLATE utf8mb3_swedish_ci DEFAULT NULL,
  `Image` varchar(255) COLLATE utf8mb3_swedish_ci DEFAULT NULL,
  PRIMARY KEY (`ID_Attraction`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_swedish_ci;

--
-- Déchargement des données de la table `attraction`
--

INSERT INTO `attraction` (`ID_Attraction`, `Nom`, `Description`, `Type_Pers`, `Video`, `Image`) VALUES
(1, 'Belenos', 'Belenos, inspiré de l\'attraction Toutatis, offre des sensations fortes dans un décor mythique. Montez à bord pour une expérience palpitante où virages serrés et accélérations impressionnantes vous attendent. Taille minimum requise : 1.30 m.', 'Sensations fortes', '', ''),
(2, 'dragon', 'Dragon, connu sous le nom de \"dragon du jardin d\'acclimatation\", vous transporte dans un univers féerique. Vivez une aventure où le mythe rencontre la réalité. Taille minimum requise : 1.20 m.', 'Découverte', '', ''),
(3, 'le carroussel', 'Le Carroussel des Héros est une attraction emblématique pour toute la famille. Embarquez pour une balade enchanteresse dans un univers de légendes, sans exigence de taille pour les petits accompagnés.', 'Famille', '', ''),
(4, 'meduse express', 'Meduse Express, équivalent de Pégase Express, combine légèreté et vitesse pour une aventure divertissante. Une attraction idéale pour découvrir les sensations en douceur. Taille minimum requise : 1.10 m.', 'Famille', '', ''),
(5, 'par isis', 'Par Isis, inspiré d\'Oziris, propose un voyage mythologique au cœur de mystères et d\'effets visuels époustouflants. L\'attraction est conçue pour offrir une montée d\'adrénaline mesurée. Taille minimum requise : 1.25 m.', 'Sensations fortes', '', ''),
(6, 'zeus', 'Zeus, reprenant les codes de Tonnerre 2 Zeus, vous promet une expérience électrisante avec des accélérations fulgurantes et des descentes vertigineuses. Idéal pour les amateurs de sensations intenses. Taille minimum requise : 1.35 m.', 'Sensations fortes', '', '');

-- --------------------------------------------------------

--
-- Structure de la table `commande`
--

DROP TABLE IF EXISTS `commande`;
CREATE TABLE IF NOT EXISTS `commande` (
  `ID_Commande` int NOT NULL AUTO_INCREMENT,
  `Date_Heure_rdv` datetime DEFAULT NULL,
  `Nb_personnes` int DEFAULT NULL,
  `Prix` decimal(6,2) DEFAULT NULL,
  `ID_Attraction` int DEFAULT NULL,
  PRIMARY KEY (`ID_Commande`),
  KEY `fk_Commande_Attraction` (`ID_Attraction`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_swedish_ci;

-- --------------------------------------------------------

--
-- Structure de la table `compte`
--

DROP TABLE IF EXISTS `compte`;
CREATE TABLE IF NOT EXISTS `compte` (
  `ID_Compte` int NOT NULL AUTO_INCREMENT,
  `Type_Compte` tinyint NOT NULL,
  `Nom` varchar(100) COLLATE utf8mb3_swedish_ci DEFAULT NULL,
  `Prenom` varchar(100) COLLATE utf8mb3_swedish_ci DEFAULT NULL,
  `Email` varchar(100) COLLATE utf8mb3_swedish_ci DEFAULT NULL,
  `MDP` varchar(100) COLLATE utf8mb3_swedish_ci DEFAULT NULL,
  PRIMARY KEY (`ID_Compte`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_swedish_ci;

--
-- Déchargement des données de la table `compte`
--

INSERT INTO `compte` (`ID_Compte`, `Type_Compte`, `Nom`, `Prenom`, `Email`, `MDP`) VALUES
(1, 2, 'Admin1', 'Admin1', 'admin1@example.com', 'adminpass1'),
(2, 2, 'Admin2', 'Admin2', 'admin2@example.com', 'adminpass2'),
(3, 1, 'User1', 'User1', 'user1@example.com', 'userpass1'),
(4, 1, 'User2', 'User2', 'user2@example.com', 'userpass2'),
(5, 1, 'User3', 'User3', 'user3@example.com', 'userpass3'),
(6, 1, 'User4', 'User4', 'user4@example.com', 'userpass4'),
(7, 0, NULL, NULL, '', NULL);

-- --------------------------------------------------------

--
-- Structure de la table `programme`
--

DROP TABLE IF EXISTS `programme`;
CREATE TABLE IF NOT EXISTS `programme` (
  `ID_Prog` int NOT NULL AUTO_INCREMENT,
  `Date_Prog` date NOT NULL,
  `Type_Jour` tinyint NOT NULL,
  PRIMARY KEY (`ID_Prog`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_swedish_ci;

-- --------------------------------------------------------

--
-- Structure de la table `reservation`
--

DROP TABLE IF EXISTS `reservation`;
CREATE TABLE IF NOT EXISTS `reservation` (
  `ID_Reserv` int NOT NULL AUTO_INCREMENT,
  `Nb_Enfant` int DEFAULT NULL,
  `Nb_Adulte` int DEFAULT NULL,
  `Nb_Bebe` int DEFAULT NULL,
  `Date_Resa` date DEFAULT NULL,
  `ID_Prog` int DEFAULT NULL,
  `ID_Compte` int DEFAULT NULL,
  PRIMARY KEY (`ID_Reserv`),
  KEY `fk_Reservation_Programme` (`ID_Prog`),
  KEY `fk_Reservation_Compte` (`ID_Compte`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_swedish_ci;

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `commande`
--
ALTER TABLE `commande`
  ADD CONSTRAINT `fk_Commande_Attraction` FOREIGN KEY (`ID_Attraction`) REFERENCES `attraction` (`ID_Attraction`) ON DELETE RESTRICT ON UPDATE CASCADE;

--
-- Contraintes pour la table `reservation`
--
ALTER TABLE `reservation`
  ADD CONSTRAINT `fk_Reservation_Compte` FOREIGN KEY (`ID_Compte`) REFERENCES `compte` (`ID_Compte`) ON DELETE RESTRICT ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_Reservation_Programme` FOREIGN KEY (`ID_Prog`) REFERENCES `programme` (`ID_Prog`) ON DELETE RESTRICT ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
