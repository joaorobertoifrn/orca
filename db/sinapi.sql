-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema sinapi
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema sinapi
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `sinapi` DEFAULT CHARACTER SET utf8 ;
-- -----------------------------------------------------
-- Schema sinapi
-- -----------------------------------------------------
USE `sinapi` ;
DROP DATABASE sinapi;
-- -----------------------------------------------------
-- Table `sinapi`.`Insumos`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sinapi`.`insumos` (
  `id` INT NOT NULL,
  `mesAno` VARCHAR(4),
  `codigo` INT,
  `descricao` VARCHAR(45),
  `unidade` VARCHAR(10),
  PRIMARY KEY (`id`))
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `sinapi`.`Classe`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sinapi`.`classe` (
  `id` INT NOT NULL,
  `descricao` VARCHAR(45) NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `sinapi`.`Composicoes`
-- -----------------------------------------------------
DROP TABLE composicoes;
CREATE TABLE IF NOT EXISTS `sinapi`.`composicoes` (
  `id_classe` INT,
  `classe` VARCHAR(100) NULL,   -- temporario
  `agrupador` INT,
  `descricao_agrupador` VARCHAR(100),
  `codigo_composicao` VARCHAR(15) NOT NULL,
  `descricao_composicao` VARCHAR(100),
  `unidade` VARCHAR(10),
  `custo_mao_obra` FLOAT,
  `mao_obra` FLOAT,
  `custo_material` FLOAT,
  `material` FLOAT,
  `custo_equipamento` FLOAT,
  `equipamento` FLOAT,
  `custo_servicos_terceiros` FLOAT,
  `servicos_terceiros` FLOAT,
  `custo_outros` FLOAT,
  `outros` FLOAT,
  PRIMARY KEY (`codigo_composicao`))
ENGINE = InnoDB;

SELECT count(*) FROM composicoes;


-- -----------------------------------------------------
-- Table `sinapi`.`Compo_Itens`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sinapi`.`composicao_itens` (
  `id` INT NOT NULL,
  `FK_codigo_composicao` INT NOT NULL,
  `tipo_item` VARCHAR(1) NULL,
  `codigo_item` VARCHAR(15) NULL,
  `descricao` VARCHAR(100) NULL,
  `coeficiente` FLOAT NULL,
  `unidade` VARCHAR(20) NULL,
  `preco_unitario` FLOAT NULL,
  `custo_total` FLOAT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_Compo_Itens_Composicoes1`
    FOREIGN KEY (`FK_codigo_composicao`)
    REFERENCES `sinapi`.`composicoes` (`codigo_composicao`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `sinapi`.`Insumo_precos`
-- -----------------------------------------------------

CREATE TABLE IF NOT EXISTS `sinapi`.`insumo_precos` (
  `mesAno_FK` VARCHAR(4) NULL,
  `idInsumo_FK` INT NULL,
  `preco` DOUBLE NULL,
  CONSTRAINT `fk_Insumo_precos_Insumos1`
    FOREIGN KEY (`idInsumo_FK` , `mesAno_FK`)
    REFERENCES `sinapi`.`insumos` (`id` , `mesAno`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;



SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
