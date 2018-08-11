DROP TABLE IF EXISTS [Pessoas];
DROP TABLE IF EXISTS [Funcoes];
DROP TABLE IF EXISTS [Empresas];
CREATE TABLE IF NOT  EXISTS [Empresas] (
    CODIGO   INTEGER       PRIMARY KEY ASC ON CONFLICT ROLLBACK AUTOINCREMENT
                           UNIQUE ON CONFLICT ROLLBACK
                           NOT NULL ON CONFLICT ROLLBACK
                           COLLATE RTRIM,
    NOME     VARCHAR (250) UNIQUE ON CONFLICT ROLLBACK
                           NOT NULL ON CONFLICT ROLLBACK
                           COLLATE RTRIM,
    CNPJ     VARCHAR (250) COLLATE RTRIM
                           NOT NULL ON CONFLICT ROLLBACK
                           UNIQUE ON CONFLICT ROLLBACK,
    ENDERECO VARCHAR (250) NOT NULL ON CONFLICT ROLLBACK
                           COLLATE RTRIM
);
CREATE TABLE IF NOT  EXISTS [Funcoes] (
    CODIGO INTEGER       PRIMARY KEY ASC ON CONFLICT ROLLBACK AUTOINCREMENT
                         UNIQUE ON CONFLICT ROLLBACK
                         NOT NULL ON CONFLICT ROLLBACK
                         COLLATE RTRIM,
    NOME   VARCHAR (250) UNIQUE ON CONFLICT ROLLBACK
                         COLLATE RTRIM
                         NOT NULL ON CONFLICT ROLLBACK
);
CREATE TABLE IF NOT  EXISTS [Pessoas] (
    CODIGO         INTEGER       PRIMARY KEY ASC ON CONFLICT ROLLBACK AUTOINCREMENT
                                 NOT NULL ON CONFLICT ROLLBACK
                                 UNIQUE ON CONFLICT ROLLBACK
                                 COLLATE RTRIM,
    NOME           VARCHAR (250) UNIQUE ON CONFLICT ROLLBACK
                                 NOT NULL ON CONFLICT ROLLBACK
                                 COLLATE RTRIM,
    CTPS           VARCHAR (250) UNIQUE ON CONFLICT ROLLBACK
                                 NOT NULL ON CONFLICT ROLLBACK
                                 COLLATE RTRIM,
    ADMISSAO       VARCHAR (250) NOT NULL ON CONFLICT ROLLBACK
                                 COLLATE RTRIM,
    FUNCAO_CODIGO  INTEGER       REFERENCES Funcoes (CODIGO) ON DELETE RESTRICT
                                                             ON UPDATE RESTRICT
                                 NOT NULL ON CONFLICT ROLLBACK
                                 COLLATE RTRIM,
    EMPRESA_CODIGO INTEGER       NOT NULL ON CONFLICT ROLLBACK
                                 COLLATE RTRIM
                                 REFERENCES Empresas (CODIGO) ON DELETE RESTRICT
                                                              ON UPDATE RESTRICT
);
