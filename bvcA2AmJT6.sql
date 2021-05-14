-- phpMyAdmin SQL Dump
-- version 4.8.5
-- https://www.phpmyadmin.net/
--
-- Servidor: localhost
-- Tiempo de generación: 14-05-2021 a las 22:21:47
-- Versión del servidor: 8.0.13-4
-- Versión de PHP: 7.2.24-0ubuntu0.18.04.7

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `bvcA2AmJT6`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `dosia`
--

CREATE TABLE `dosia` (
  `GAIXONAN` int(11) DEFAULT NULL,
  `KODEA` int(11) NOT NULL,
  `IZENA` varchar(15) DEFAULT NULL,
  `MARKA` varchar(15) DEFAULT NULL,
  `IRAUNGIDATA` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Volcado de datos para la tabla `dosia`
--

INSERT INTO `dosia` (`GAIXONAN`, `KODEA`, `IZENA`, `MARKA`, `IRAUNGIDATA`) VALUES
(23456789, 123, 'Paracetamol', 'BAYER', '2022-02-03'),
(23456790, 124, 'Crema Labial', 'Zovicrem', '2021-11-04'),
(23456791, 125, 'Fondaparinux', 'Arixtra', '2021-05-30'),
(23456792, 126, 'Isoflurano', 'Isoflutek', '2022-02-06'),
(23456791, 127, 'Pilula', 'LOETTE', '2023-08-08'),
(23456789, 129, 'Metformin', 'Metformin', '2022-02-09'),
(23456796, 130, 'Rivasrigmina', 'EXELON', '2021-12-10'),
(23456789, 131, 'Txaplata', 'VoltaTermic', '2021-05-29');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `gaixoa`
--

CREATE TABLE `gaixoa` (
  `NAN` int(11) NOT NULL,
  `SSZENB` bigint(20) DEFAULT NULL,
  `IZENA` varchar(15) NOT NULL,
  `ABIZENA` varchar(15) DEFAULT NULL,
  `SEXUA` varchar(8) DEFAULT NULL,
  `JAIODATA` date DEFAULT NULL,
  `OHIKOZENTROA` varchar(15) DEFAULT NULL,
  `HOSPITALEANDAGO` tinyint(1) DEFAULT NULL,
  `NONBIZI` varchar(15) DEFAULT NULL,
  `ADINA` int(11) DEFAULT NULL,
  `TELF` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Volcado de datos para la tabla `gaixoa`
--

INSERT INTO `gaixoa` (`NAN`, `SSZENB`, `IZENA`, `ABIZENA`, `SEXUA`, `JAIODATA`, `OHIKOZENTROA`, `HOSPITALEANDAGO`, `NONBIZI`, `ADINA`, `TELF`) VALUES
(23456789, 123456789012, 'Janire', 'Martikorena', 'E', '1945-11-30', 'Deusto', 0, 'Bilbao', NULL, 666565566),
(23456790, 123456789013, 'Nerea', 'Garmendia', 'E', '2020-02-29', 'Deusto', 0, 'Bilbao', NULL, 666565567),
(23456791, 123456789014, 'Irati', 'Alonso', 'E', '2003-02-13', 'Basurto', 1, 'Bilbao', NULL, 666565568),
(23456792, 123456789015, 'Jon', 'Garcia', 'G', '1965-12-01', 'Basurto', 1, 'Bilbao', NULL, 666565569),
(23456793, 123456789016, 'Edorta', 'Medina', 'G', '1999-01-02', 'Basurto', 0, 'Bilbao', NULL, 666565570),
(23456794, 123456789017, 'David', 'Garay', 'G', '2002-12-03', 'Deusto', 0, 'Bilbao', NULL, 666565571),
(23456795, 123456789018, 'Ane', 'Pascual', 'X', '2002-12-04', 'Begoña', 0, 'Bilbao', NULL, 666565572),
(23456796, 123456789019, 'Saioa', 'Bilbao', 'E', '2002-12-05', 'Deusto', 1, 'Bilbao', NULL, 666565573),
(23456797, 123456789020, 'Eneko', 'Perez', 'G', '2001-03-06', 'Deusto', 0, 'Bilbao', NULL, 666565574),
(23456798, 123456789021, 'Alex', 'Suarez', 'X', '2012-10-22', 'Deusto', 0, 'Bilbao', NULL, 666565575);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `medikua`
--

CREATE TABLE `medikua` (
  `NAN` int(11) NOT NULL,
  `KOLEGIATUZENB` int(11) DEFAULT NULL,
  `IZENA` varchar(15) NOT NULL,
  `ABIZENA` varchar(15) DEFAULT NULL,
  `ZENTROA` varchar(15) DEFAULT NULL,
  `JAIODATA` date DEFAULT NULL,
  `BADAFAMILIAKOMEDIKUA` tinyint(1) DEFAULT NULL,
  `ESPEZIALITATEA` varchar(15) DEFAULT NULL,
  `TELF` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Volcado de datos para la tabla `medikua`
--

INSERT INTO `medikua` (`NAN`, `KOLEGIATUZENB`, `IZENA`, `ABIZENA`, `ZENTROA`, `JAIODATA`, `BADAFAMILIAKOMEDIKUA`, `ESPEZIALITATEA`, `TELF`) VALUES
(12345678, 123456789, 'Juan ', 'Lopez', 'Deusto', '1996-01-12', 1, NULL, 666666666),
(12345679, 246913578, 'Lorena', 'Garcia', 'Deusto', '1986-02-13', 1, NULL, 666666667),
(12345680, 493827156, 'Maria', 'Bilbao', 'Deusto', '1969-03-24', 1, 'Pediatria', 666666668),
(12345681, 987654312, 'Ander', 'Aguirre', 'Deusto', '1966-05-28', 0, 'Dermatologia', 666666669),
(12345682, 134567890, 'Leire', 'Perez', 'Deusto', '1996-04-16', 0, 'Traumatologia', 666666670),
(12345683, 269135780, 'Amaia', 'Gonzalez', 'Deusto', '1995-06-17', 0, 'Neurologia', 666666671),
(12345684, 538271560, 'Markel', 'Alonso', 'Begoña', '1963-10-06', 1, NULL, 666666672),
(12345685, 145678901, 'Jose', 'Garcia', 'Deusto', '1988-07-01', 0, 'Kardiologia', 666666673),
(12345686, 291357802, 'Ander', 'Martinez', 'Deusto', '1996-11-27', 0, 'Geriatria', 666666675),
(12345687, 582715604, 'Maria', 'Fernandez', 'Basurto', '1977-08-02', 1, NULL, 666666676),
(12345688, 156789012, 'Iker', 'Calahorra', 'Deusto', '1989-09-23', 0, 'Psikiatria', 666666677),
(12345689, 313578024, 'Nahia', 'Mentxaka', 'Deusto', '1996-01-12', 0, 'Neurologia', 666666678);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `zita`
--

CREATE TABLE `zita` (
  `MEDIKUNAN` int(11) DEFAULT NULL,
  `GAIXONAN` int(11) DEFAULT NULL,
  `DATA` date DEFAULT NULL,
  `ORDUA` time DEFAULT NULL,
  `LEKUA` varchar(15) DEFAULT NULL,
  `GELA` varchar(4) DEFAULT NULL,
  `PRESENTZIALKI` tinyint(1) DEFAULT NULL,
  `ONARTU` tinyint(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Volcado de datos para la tabla `zita`
--

INSERT INTO `zita` (`MEDIKUNAN`, `GAIXONAN`, `DATA`, `ORDUA`, `LEKUA`, `GELA`, `PRESENTZIALKI`, `ONARTU`) VALUES
(12345678, 23456789, '2021-05-12', '08:30:00', NULL, NULL, 0, 1),
(12345679, 23456790, '2021-05-12', '09:30:00', 'Deusto', 'P23I', 1, 1),
(12345680, 23456791, '2021-05-19', '10:30:00', 'Deusto', 'P71I', 1, 1),
(12345678, 23456792, '2021-05-14', '11:30:00', NULL, NULL, 0, 1),
(12345678, 23456793, '2021-05-19', '12:30:00', 'Deusto', 'P73I', 1, 1),
(12345678, 23456795, '2021-05-17', '10:30:00', 'Deusto', 'P34I', 1, 1),
(12345686, 23456797, '2021-05-17', '10:30:00', 'Deusto', 'P73I', 1, 0),
(12345678, 23456790, '2021-05-30', '11:00:00', NULL, NULL, 0, 0),
(12345681, 23456791, '2022-01-01', NULL, NULL, NULL, NULL, NULL),
(12345684, 23456796, '2023-02-20', NULL, NULL, NULL, NULL, NULL),
(12345688, 23456793, '2022-11-13', NULL, NULL, NULL, NULL, NULL),
(12345682, 23456792, '2024-01-12', NULL, NULL, NULL, NULL, NULL),
(12345686, 23456789, '2022-06-02', NULL, NULL, NULL, NULL, NULL),
(12345680, 23456794, '2022-12-11', NULL, NULL, NULL, NULL, NULL);

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `dosia`
--
ALTER TABLE `dosia`
  ADD PRIMARY KEY (`KODEA`),
  ADD KEY `GAIXONAN` (`GAIXONAN`);

--
-- Indices de la tabla `gaixoa`
--
ALTER TABLE `gaixoa`
  ADD PRIMARY KEY (`NAN`),
  ADD UNIQUE KEY `SSZENB` (`SSZENB`);

--
-- Indices de la tabla `medikua`
--
ALTER TABLE `medikua`
  ADD PRIMARY KEY (`NAN`),
  ADD UNIQUE KEY `KOLEGIATUZENB` (`KOLEGIATUZENB`);

--
-- Indices de la tabla `zita`
--
ALTER TABLE `zita`
  ADD KEY `MEDIKUNAN` (`MEDIKUNAN`),
  ADD KEY `GAIXONAN` (`GAIXONAN`);

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `dosia`
--
ALTER TABLE `dosia`
  ADD CONSTRAINT `dosia_ibfk_1` FOREIGN KEY (`GAIXONAN`) REFERENCES `gaixoa` (`nan`);

--
-- Filtros para la tabla `zita`
--
ALTER TABLE `zita`
  ADD CONSTRAINT `zita_ibfk_1` FOREIGN KEY (`MEDIKUNAN`) REFERENCES `medikua` (`nan`),
  ADD CONSTRAINT `zita_ibfk_2` FOREIGN KEY (`GAIXONAN`) REFERENCES `gaixoa` (`nan`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
