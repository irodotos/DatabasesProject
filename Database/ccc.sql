-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Εξυπηρετητής: 127.0.0.1
-- Χρόνος δημιουργίας: 24 Ιαν 2022 στις 15:36:37
-- Έκδοση διακομιστή: 10.4.22-MariaDB
-- Έκδοση PHP: 8.1.1

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Βάση δεδομένων: `ccc`
--

-- --------------------------------------------------------

--
-- Δομή πίνακα για τον πίνακα `corporation`
--

CREATE TABLE `corporation` (
  `account_id` bigint(20) NOT NULL,
  `expiration_date` varchar(10) NOT NULL,
  `amount_owed` decimal(10,0) NOT NULL DEFAULT 0,
  `balance` decimal(10,0) NOT NULL,
  `credit_limit` decimal(10,0) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Άδειασμα δεδομένων του πίνακα `corporation`
--

INSERT INTO `corporation` (`account_id`, `expiration_date`, `amount_owed`, `balance`, `credit_limit`) VALUES
(1010101010101010, '25/04/2030', '0', '50000', '50000'),
(2111111111111111, '02/10/2027', '0', '1000000', '1000000'),
(4444444444444444, '26/03/2022', '0', '500000', '500000'),
(5555555555555555, '02/06/2023', '0', '100000', '100000'),
(6666666666666666, '16/01/2022', '249000', '751000', '1000000'),
(8787878787878787, '03/04/2022', '10000', '490000', '500000');

-- --------------------------------------------------------

--
-- Δομή πίνακα για τον πίνακα `corp_employee`
--

CREATE TABLE `corp_employee` (
  `account_id` bigint(20) NOT NULL,
  `corporation_name` varchar(50) NOT NULL,
  `id` int(11) NOT NULL,
  `emp_name` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Άδειασμα δεδομένων του πίνακα `corp_employee`
--

INSERT INTO `corp_employee` (`account_id`, `corporation_name`, `id`, `emp_name`) VALUES
(2111111111111111, 'ADP', 1230, 'Skyla Whitney'),
(6666666666666666, 'CMS Energy', 3111, 'Clarke Johnson'),
(1010101010101010, 'XPO Logistics', 5566, 'Taylor Walker '),
(4444444444444444, 'LaCorp', 7432, 'Bailey  Scott '),
(5555555555555555, 'Autoliv', 8899, 'Martin Hill');

-- --------------------------------------------------------

--
-- Δομή πίνακα για τον πίνακα `dealer`
--

CREATE TABLE `dealer` (
  `account_id` bigint(20) NOT NULL,
  `amount_owed` decimal(10,0) NOT NULL DEFAULT 0,
  `commission` decimal(10,0) NOT NULL,
  `profit` decimal(10,0) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Άδειασμα δεδομένων του πίνακα `dealer`
--

INSERT INTO `dealer` (`account_id`, `amount_owed`, `commission`, `profit`) VALUES
(888888888888888, '0', '10', '1209'),
(1000000000000000, '51', '5', '1920'),
(3333333333333333, '4500', '4', '2000'),
(5454545454545454, '500', '3', '3000'),
(7878787878787878, '102', '10', '2516');

-- --------------------------------------------------------

--
-- Δομή πίνακα για τον πίνακα `individual`
--

CREATE TABLE `individual` (
  `account_id` bigint(20) NOT NULL,
  `expiration_date` varchar(10) NOT NULL,
  `amount_owed` decimal(10,0) NOT NULL DEFAULT 0,
  `balance` decimal(10,0) NOT NULL,
  `credit_limit` decimal(10,0) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Άδειασμα δεδομένων του πίνακα `individual`
--

INSERT INTO `individual` (`account_id`, `expiration_date`, `amount_owed`, `balance`, `credit_limit`) VALUES
(2222222222222222, '09/12/2023', '19500', '30500', '50000'),
(2323232323232323, '14/03/2022', '0', '15000', '15000'),
(5566778899112233, '01/03/2022', '0', '60000', '60000'),
(7777777777777777, '31/01/2024', '100000', '400000', '500000'),
(7979797979797979, '29/06/2022', '0', '5000', '5000'),
(9999999999999999, '28/02/2022', '0', '10000', '10000');

-- --------------------------------------------------------

--
-- Δομή πίνακα για τον πίνακα `transaction`
--

CREATE TABLE `transaction` (
  `transaction_id` bigint(20) NOT NULL,
  `date` varchar(10) NOT NULL,
  `type` varchar(20) NOT NULL,
  `amount` decimal(10,0) NOT NULL,
  `client_name` varchar(50) NOT NULL,
  `dealer_name` varchar(50) NOT NULL,
  `client_acc_id` bigint(20) NOT NULL,
  `dealer_acc_id` bigint(20) NOT NULL,
  `corp_emp_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Άδειασμα δεδομένων του πίνακα `transaction`
--

INSERT INTO `transaction` (`transaction_id`, `date`, `type`, `amount`, `client_name`, `dealer_name`, `client_acc_id`, `dealer_acc_id`, `corp_emp_id`) VALUES
(1002, '18/02/2022', 'credit', '1000', 'Daniel Potter', 'Lara Weasley', 9999999999999999, 7878787878787878, NULL),
(1003, '24/11/2022', 'charge', '200', 'Martin Hill', 'James Smith', 5555555555555555, 1000000000000000, 8899),
(1010, '21/01/2022', 'credit', '3000', 'Taylor Walker ', 'Leona Rose', 1010101010101010, 3333333333333333, 5566),
(1012, '18/01/2022', 'charge', '500', 'Ivan Gilbert', 'Perry Miles', 2222222222222222, 888888888888888, NULL),
(1016, '19/11/2022', 'charge', '200', 'Takis Zn', 'Lara Weasley', 5566778899112233, 7878787878787878, NULL),
(1023, '17/03/2022', 'charge', '100', 'Takis Zn', 'Lara Weasley', 5566778899112233, 7878787878787878, NULL),
(1029, '20/02/2022', 'credit', '20', 'ADP', 'Perry Miles', 2111111111111111, 888888888888888, NULL),
(1030, '02/03/2023', 'credit', '123', 'ADP', 'Perry Miles', 2111111111111111, 888888888888888, NULL),
(1032, '21/07/2022', 'charge', '100', 'Autoliv', 'Perry Miles', 5555555555555555, 888888888888888, 8899),
(1036, '16/02/2022', 'charge', '100', 'ADP', 'Perry Miles', 2111111111111111, 888888888888888, 1230),
(1037, '30/04/2021', 'charge', '100', 'ADP', 'Perry Miles', 2111111111111111, 888888888888888, NULL),
(1038, '12/12/2019', 'charge', '3300', 'Takis Zn', 'Lara Weasley', 5566778899112233, 7878787878787878, NULL),
(1039, '12/12/2019', 'credit', '3300', 'Takis Zn', 'Lara Weasley', 5566778899112233, 7878787878787878, NULL);

-- --------------------------------------------------------

--
-- Δομή πίνακα για τον πίνακα `users`
--

CREATE TABLE `users` (
  `account_id` bigint(20) NOT NULL,
  `name` varchar(50) NOT NULL,
  `type` varchar(15) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Άδειασμα δεδομένων του πίνακα `users`
--

INSERT INTO `users` (`account_id`, `name`, `type`) VALUES
(888888888888888, 'Perry Miles', 'dealer'),
(1000000000000000, 'James Smith', 'dealer'),
(1010101010101010, 'XPO Logistics', 'corporation'),
(2111111111111111, 'ADP', 'corporation'),
(2222222222222222, 'Ivan Gilbert', 'individual'),
(2323232323232323, 'Ellis Mcdonald', 'individual'),
(3333333333333333, 'Leona Rose', 'dealer'),
(4444444444444444, 'LabCorp', 'corporation'),
(5454545454545454, 'Murray Cox', 'dealer'),
(5555555555555555, 'Autoliv', 'corporation'),
(5566778899112233, 'Takis Zn', 'individual'),
(6666666666666666, 'CMS Energy', 'corporation'),
(7777777777777777, 'Alexia Anderson', 'individual'),
(7878787878787878, 'Lara Weasley', 'dealer'),
(7979797979797979, 'Flores Bell', 'individual'),
(8787878787878787, 'ADR', 'corporation'),
(9999999999999999, 'Daniel Potter', 'individual');

--
-- Ευρετήρια για άχρηστους πίνακες
--

--
-- Ευρετήρια για πίνακα `corporation`
--
ALTER TABLE `corporation`
  ADD PRIMARY KEY (`account_id`);

--
-- Ευρετήρια για πίνακα `corp_employee`
--
ALTER TABLE `corp_employee`
  ADD PRIMARY KEY (`id`),
  ADD KEY `foreign_corp_employee_id` (`account_id`);

--
-- Ευρετήρια για πίνακα `dealer`
--
ALTER TABLE `dealer`
  ADD PRIMARY KEY (`account_id`);

--
-- Ευρετήρια για πίνακα `individual`
--
ALTER TABLE `individual`
  ADD PRIMARY KEY (`account_id`);

--
-- Ευρετήρια για πίνακα `transaction`
--
ALTER TABLE `transaction`
  ADD PRIMARY KEY (`transaction_id`),
  ADD KEY `foreign_client_acc_id` (`client_acc_id`),
  ADD KEY `foreign_dealer_acc_id` (`dealer_acc_id`),
  ADD KEY `foreign_employee_id` (`corp_emp_id`);

--
-- Ευρετήρια για πίνακα `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`account_id`);

--
-- Περιορισμοί για άχρηστους πίνακες
--

--
-- Περιορισμοί για πίνακα `corp_employee`
--
ALTER TABLE `corp_employee`
  ADD CONSTRAINT `foreign_corp_employee_id` FOREIGN KEY (`account_id`) REFERENCES `users` (`account_id`);

--
-- Περιορισμοί για πίνακα `transaction`
--
ALTER TABLE `transaction`
  ADD CONSTRAINT `foreign_client_acc_id` FOREIGN KEY (`client_acc_id`) REFERENCES `users` (`account_id`),
  ADD CONSTRAINT `foreign_dealer_acc_id` FOREIGN KEY (`dealer_acc_id`) REFERENCES `users` (`account_id`),
  ADD CONSTRAINT `foreign_employee_id` FOREIGN KEY (`corp_emp_id`) REFERENCES `corp_employee` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
