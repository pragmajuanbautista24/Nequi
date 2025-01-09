-- Tabla Franquicia
CREATE TABLE Franquicia (
                            id SERIAL PRIMARY KEY,
                            nombre VARCHAR(100) NOT NULL
);

-- Tabla Sucursal
CREATE TABLE Sucursal (
                          id SERIAL PRIMARY KEY,
                          nombre VARCHAR(100) NOT NULL
);

-- Tabla Producto
CREATE TABLE Producto (
                          id SERIAL PRIMARY KEY,
                          nombre VARCHAR(100) NOT NULL,
                          cantidad INT NOT NULL
);

ALTER TABLE Sucursal ADD COLUMN franquicia_id INTEGER;
ALTER TABLE Producto ADD COLUMN sucursal_id INTEGER;

ALTER TABLE Sucursal ADD CONSTRAINT fk_sucursal_franquicia
    FOREIGN KEY (franquicia_id) REFERENCES franquicia(id);

ALTER TABLE Producto ADD CONSTRAINT fk_sucursal_franquicia
    FOREIGN KEY (sucursal_id) REFERENCES Sucursal(id);