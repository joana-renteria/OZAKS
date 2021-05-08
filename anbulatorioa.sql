-- phpMyAdmin SQL Dump
-- version 5.0.4
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 08-05-2021 a las 18:09:43
-- Versión del servidor: 10.4.17-MariaDB
-- Versión de PHP: 7.4.15

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `anbulatorioa`
--
CREATE DATABASE IF NOT EXISTS `anbulatorioa` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `anbulatorioa`;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `botika`
--

CREATE TABLE `botika` (
  `GAIXONAN` int(11) DEFAULT NULL,
  `SSZENB` int(11) DEFAULT NULL,
  `KODEA` int(11) NOT NULL,
  `IZENA` varchar(15) DEFAULT NULL,
  `MARKA` varchar(15) DEFAULT NULL,
  `DOSIKOP` float DEFAULT NULL,
  `IRAUNGIDATA` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `gaixoa`
--

CREATE TABLE `gaixoa` (
  `NAN` int(11) NOT NULL,
  `SSZENB` int(11) NOT NULL,
  `IZENA` varchar(15) NOT NULL,
  `ABIZENA` varchar(15) DEFAULT NULL,
  `SEXUA` varchar(8) DEFAULT NULL,
  `JAIODATA` date DEFAULT NULL,
  `OHIKOZENTROA` varchar(15) DEFAULT NULL,
  `HOSPITALEANDAGO` tinyint(1) DEFAULT NULL,
  `NONBIZI` varchar(15) DEFAULT NULL,
  `ADINA` int(11) DEFAULT NULL,
  `TELF` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `medikua`
--

CREATE TABLE `medikua` (
  `NAN` int(11) NOT NULL,
  `KOLEGIATUZENB` int(11) NOT NULL,
  `IZENA` varchar(15) NOT NULL,
  `ABIZENA` varchar(15) DEFAULT NULL,
  `ZENTROA` varchar(15) DEFAULT NULL,
  `JAIODATA` date DEFAULT NULL,
  `BADAFAMILIAKOMEDIKUA` tinyint(1) DEFAULT NULL,
  `ESPEZIALITATEA` varchar(15) DEFAULT NULL,
  `TELF` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `zita`
--

CREATE TABLE `zita` (
  `MEDIKUNAN` int(11) DEFAULT NULL,
  `KOLEGIATUZENB` int(11) DEFAULT NULL,
  `GAIXONAN` int(11) DEFAULT NULL,
  `SSZENB` int(11) DEFAULT NULL,
  `DATA` date DEFAULT NULL,
  `ORDUA` time DEFAULT NULL,
  `LEKUA` varchar(15) DEFAULT NULL,
  `GELA` varchar(4) DEFAULT NULL,
  `PRESENTZIALKI` tinyint(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `botika`
--
ALTER TABLE `botika`
  ADD PRIMARY KEY (`KODEA`),
  ADD KEY `GAIXONAN` (`GAIXONAN`,`SSZENB`);

--
-- Indices de la tabla `gaixoa`
--
ALTER TABLE `gaixoa`
  ADD PRIMARY KEY (`NAN`,`SSZENB`);

--
-- Indices de la tabla `medikua`
--
ALTER TABLE `medikua`
  ADD PRIMARY KEY (`NAN`,`KOLEGIATUZENB`);

--
-- Indices de la tabla `zita`
--
ALTER TABLE `zita`
  ADD KEY `MEDIKUNAN` (`MEDIKUNAN`,`KOLEGIATUZENB`),
  ADD KEY `GAIXONAN` (`GAIXONAN`,`SSZENB`);

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `botika`
--
ALTER TABLE `botika`
  ADD CONSTRAINT `botika_ibfk_1` FOREIGN KEY (`GAIXONAN`,`SSZENB`) REFERENCES `gaixoa` (`NAN`, `SSZENB`);

--
-- Filtros para la tabla `zita`
--
ALTER TABLE `zita`
  ADD CONSTRAINT `zita_ibfk_1` FOREIGN KEY (`MEDIKUNAN`,`KOLEGIATUZENB`) REFERENCES `medikua` (`NAN`, `KOLEGIATUZENB`),
  ADD CONSTRAINT `zita_ibfk_2` FOREIGN KEY (`GAIXONAN`,`SSZENB`) REFERENCES `gaixoa` (`NAN`, `SSZENB`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
