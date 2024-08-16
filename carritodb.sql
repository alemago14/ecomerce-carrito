-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 16-08-2024 a las 21:24:38
-- Versión del servidor: 10.4.27-MariaDB
-- Versión de PHP: 8.1.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `carritodb`
--
CREATE DATABASE IF NOT EXISTS `carritodb` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `carritodb`;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `carrito`
--

CREATE TABLE `carrito` (
  `id` varchar(255) NOT NULL,
  `descuento` double NOT NULL,
  `fecha_compra` date DEFAULT NULL,
  `tipo_carrito` enum('NORMAL','PROMOCIONAL','VIP') DEFAULT NULL,
  `total` double NOT NULL,
  `total_parcial` double NOT NULL,
  `usuario_id` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `carrito`
--

INSERT INTO `carrito` (`id`, `descuento`, `fecha_compra`, `tipo_carrito`, `total`, `total_parcial`, `usuario_id`) VALUES
('03ce5ba1-d70e-4422-97bd-2a41f83cdeea', 0, '2024-08-16', 'PROMOCIONAL', 2200, 2200, '27f30fdb-967e-4fa5-b9f1-e0402bce2ca7'),
('45a490aa-c6e5-497d-b526-d5430ad1d20f', 3545.75, '2024-08-16', 'PROMOCIONAL', 14537.25, 18083, '27f30fdb-967e-4fa5-b9f1-e0402bce2ca7'),
('866a0de5-f984-4bc0-9a9e-1ad68a05f49a', 1400, '2024-07-14', 'NORMAL', 5900, 7300, '27f30fdb-967e-4fa5-b9f1-e0402bce2ca7'),
('b09ff474-34fd-4281-a4d9-001a1522eb87', 0, '2024-08-16', 'PROMOCIONAL', 5100, 5100, '27f30fdb-967e-4fa5-b9f1-e0402bce2ca7'),
('ea0bd87b-a0b5-4d98-9420-07951907244f', 0, '2024-08-16', 'PROMOCIONAL', 11500, 11500, '27f30fdb-967e-4fa5-b9f1-e0402bce2ca7');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `fechas_promocionales`
--

CREATE TABLE `fechas_promocionales` (
  `id` varchar(255) NOT NULL,
  `descripcion` varchar(255) DEFAULT NULL,
  `fecha_fin` date DEFAULT NULL,
  `fecha_inicio` date DEFAULT NULL,
  `nombre` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `fechas_promocionales`
--

INSERT INTO `fechas_promocionales` (`id`, `descripcion`, `fecha_fin`, `fecha_inicio`, `nombre`) VALUES
('7e5fb664-2fc0-4b51-b8ac-e47d29b637a1', 'Promo especial', '2024-08-20', '2024-08-10', 'Sale Time');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `item_carrito`
--

CREATE TABLE `item_carrito` (
  `id` varchar(255) NOT NULL,
  `cantidad` int(11) NOT NULL,
  `precio_total` double NOT NULL,
  `precio_unitario` double NOT NULL,
  `carrito_id` varchar(255) DEFAULT NULL,
  `producto_id` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `item_carrito`
--

INSERT INTO `item_carrito` (`id`, `cantidad`, `precio_total`, `precio_unitario`, `carrito_id`, `producto_id`) VALUES
('25703860-8141-4f5c-891e-52d85bed47b2', 1, 8100, 8100, '45a490aa-c6e5-497d-b526-d5430ad1d20f', '03211a5a-5b8b-4f33-bfd7-84cfa08c5686'),
('3b6dec0c-ff6a-46b6-b367-05a7bd318e26', 1, 8100, 8100, 'ea0bd87b-a0b5-4d98-9420-07951907244f', '03211a5a-5b8b-4f33-bfd7-84cfa08c5686'),
('5028f573-9c18-4dd2-9c80-e2f75f0a8009', 1, 6083, 6083, '45a490aa-c6e5-497d-b526-d5430ad1d20f', 'c1ea4604-3fb4-4743-8340-69c9f63b9e49'),
('70f4b330-cbbe-4e90-a9c1-4356fc51e66a', 1, 1200, 1200, 'b09ff474-34fd-4281-a4d9-001a1522eb87', '74e72bc8-bee4-4c80-b0ac-5fc2c55ce301'),
('7d819623-14ea-4276-91b1-4ad431490161', 1, 1200, 1200, 'ea0bd87b-a0b5-4d98-9420-07951907244f', '74e72bc8-bee4-4c80-b0ac-5fc2c55ce301'),
('7e149e9a-d8f8-4bdc-b607-5a88fc81680b', 1, 1700, 1700, 'b09ff474-34fd-4281-a4d9-001a1522eb87', '5d0b990a-dc24-4d2c-8a7c-404ab3801d3c'),
('8ee5bc33-6db5-4eee-8b82-2ac046920596', 1, 2200, 2200, '866a0de5-f984-4bc0-9a9e-1ad68a05f49a', '93844774-1670-478d-b994-62c975f6d2b0'),
('97f6a361-1268-47a9-9d66-ea599d9321dc', 1, 2200, 2200, 'ea0bd87b-a0b5-4d98-9420-07951907244f', '93844774-1670-478d-b994-62c975f6d2b0'),
('aa18dce4-333d-45e5-8486-5e58479b2056', 2, 3400, 1700, '866a0de5-f984-4bc0-9a9e-1ad68a05f49a', '5d0b990a-dc24-4d2c-8a7c-404ab3801d3c'),
('bce68621-b9bd-4274-a233-b26ca711eafb', 1, 2200, 2200, 'b09ff474-34fd-4281-a4d9-001a1522eb87', '93844774-1670-478d-b994-62c975f6d2b0'),
('c28f8538-d66b-4caa-a92a-6172406c8536', 1, 2200, 2200, '45a490aa-c6e5-497d-b526-d5430ad1d20f', '93844774-1670-478d-b994-62c975f6d2b0'),
('d08a0ed5-fcaa-4a59-b2e3-79b936c889ac', 1, 1700, 1700, '866a0de5-f984-4bc0-9a9e-1ad68a05f49a', 'dcba3752-6a18-4c15-bc5f-0bd775be1083'),
('d8c7a40f-9903-4651-a79f-5bbfbac41274', 1, 1700, 1700, '45a490aa-c6e5-497d-b526-d5430ad1d20f', '5d0b990a-dc24-4d2c-8a7c-404ab3801d3c'),
('e82de412-59c5-4a8d-96af-b4d5eefc8e8a', 1, 2200, 2200, '03ce5ba1-d70e-4422-97bd-2a41f83cdeea', '93844774-1670-478d-b994-62c975f6d2b0');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `producto`
--

CREATE TABLE `producto` (
  `id` varchar(255) NOT NULL,
  `descripcion` varchar(255) DEFAULT NULL,
  `nombre` varchar(255) DEFAULT NULL,
  `precio` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `producto`
--

INSERT INTO `producto` (`id`, `descripcion`, `nombre`, `precio`) VALUES
('03211a5a-5b8b-4f33-bfd7-84cfa08c5686', 'Ceveza', 'Cerveza Andes Lata x6u', 8100),
('5d0b990a-dc24-4d2c-8a7c-404ab3801d3c', 'Gaseosa', 'Gaseosa Talca Cola 3L', 1700),
('74e72bc8-bee4-4c80-b0ac-5fc2c55ce301', 'Gaseosa', 'Gaseosa Manaos Naranja 3L', 1200),
('93844774-1670-478d-b994-62c975f6d2b0', 'Gaseosa', 'Gaseosa Sprite 2L', 2200),
('c1ea4604-3fb4-4743-8340-69c9f63b9e49', 'Ceveza', 'Cerveza Andes Lata x4u', 6083),
('dcba3752-6a18-4c15-bc5f-0bd775be1083', 'Gaseosa', 'Gaseosa Talca Naranja 3L', 1700),
('e370b74a-1847-46f1-ac74-331542d671e2', 'Gaseosa', 'Gaseosa Manaos Cola 3L', 1200),
('e4381cd3-e7d1-4fbd-a2a4-9823604dd376', 'Gaseosa', 'Gaseosa Coca-Cola 2L', 2500);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuario`
--

CREATE TABLE `usuario` (
  `id` varchar(255) NOT NULL,
  `password` varchar(255) DEFAULT NULL,
  `rol` enum('ROLE_ADMINISTRADOR','ROLE_NORMAL','ROLE_VIP') DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `usuario`
--

INSERT INTO `usuario` (`id`, `password`, `rol`, `username`) VALUES
('27f30fdb-967e-4fa5-b9f1-e0402bce2ca7', '$2a$10$RyhSkjGg2BErmfISs/qOPedf3g.JsYreqVov.CFMQ9N8gxGsQ2NY6', 'ROLE_NORMAL', 'ale'),
('6777ec72-2fa7-4881-8f25-95e6354b8bf4', '$2a$10$gfPzkIem7CEqedmCG7S1Oet3b8HWm.3wOpVCbnlkVkVHuUM9wJci2', 'ROLE_ADMINISTRADOR', 'admin'),
('eb51e69f-20d8-49dc-b31b-4eaf7d43b3a7', '$2a$10$6A8FT3pM3f4FlH3qjCIqQO2XC/7WG3ljwJiSgkVtYGel.UdcRUgIS', 'ROLE_NORMAL', 'usuario');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `carrito`
--
ALTER TABLE `carrito`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK8ymop2vbmxmjq6ehl5vj3hpqm` (`usuario_id`);

--
-- Indices de la tabla `fechas_promocionales`
--
ALTER TABLE `fechas_promocionales`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `item_carrito`
--
ALTER TABLE `item_carrito`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK7jlhnuuh4m7q1cn77xhtx9kdq` (`carrito_id`),
  ADD KEY `FKgarw89vvyxd65d4bqvwwmobud` (`producto_id`);

--
-- Indices de la tabla `producto`
--
ALTER TABLE `producto`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `usuario`
--
ALTER TABLE `usuario`
  ADD PRIMARY KEY (`id`);

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `carrito`
--
ALTER TABLE `carrito`
  ADD CONSTRAINT `FK8ymop2vbmxmjq6ehl5vj3hpqm` FOREIGN KEY (`usuario_id`) REFERENCES `usuario` (`id`);

--
-- Filtros para la tabla `item_carrito`
--
ALTER TABLE `item_carrito`
  ADD CONSTRAINT `FK7jlhnuuh4m7q1cn77xhtx9kdq` FOREIGN KEY (`carrito_id`) REFERENCES `carrito` (`id`),
  ADD CONSTRAINT `FKgarw89vvyxd65d4bqvwwmobud` FOREIGN KEY (`producto_id`) REFERENCES `producto` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
