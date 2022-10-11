Create DATABASE vuelos;

USE vuelos;


CREATE TABLE ubicaciones(
	pais	VARCHAR(20) NOT NULL,
	estado	VARCHAR(20) NOT NULL,
	ciudad	VARCHAR(20) NOT NULL,
	huso	SMALLINT	NOT NULL
			CHECK (huso>=-12 AND huso<=12),
	
	CONSTRAINT pk_ubicaciones
	PRIMARY KEY (pais, estado, ciudad)

)ENGINE=InnoDB;


CREATE TABLE modelos_avion(
	modelo			VARCHAR(20)	NOT NULL,
	fabricante		VARCHAR(20) NOT NULL,
	cabinas			SMALLINT 	UNSIGNED NOT NULL,
	cant_asientos 	SMALLINT 	UNSIGNED NOT NULL,

	CONSTRAINT pk_modelos_avion
	PRIMARY KEY (modelo)
	
)ENGINE=InnoDB;


CREATE TABLE clases (
	nombre 		VARCHAR(20)		NOT NULL,
	porcentaje 	DECIMAL(2,2)	UNSIGNED NOT NULL,
	
	CONSTRAINT pk_clases
	PRIMARY KEY (nombre)

) ENGINE=InnoDB;


CREATE TABLE comodidades (
	codigo		SMALLINT	UNSIGNED NOT NULL,
	descripcion TEXT		NOT NULL,
	
	CONSTRAINT pk_comodidades
	PRIMARY KEY (codigo)

) ENGINE=InnoDB;


CREATE TABLE pasajeros (
	doc_tipo		VARCHAR(20)	NOT NULL, 
	doc_nro 		SMALLINT 	UNSIGNED NOT NULL,
	apellido 		VARCHAR(20) NOT NULL, 
	nombre 			VARCHAR(20) NOT NULL, 
	direccion 		VARCHAR(40) NOT NULL, 
	telefono 		VARCHAR(15) NOT NULL, 
	nacionalidad 	VARCHAR(20) NOT NULL,

	CONSTRAINT pk_pasajeros
	PRIMARY KEY (doc_tipo, doc_nro)

) ENGINE=InnoDB;


CREATE TABLE empleados (
	legajo 		SMALLINT 	UNSIGNED NOT NULL, 
	password 	VARCHAR(32) NOT NULL,
	doc_tipo 	VARCHAR(20) NOT NULL, 
	doc_nro 	SMALLINT 	UNSIGNED NOT NULL, 
	apellido 	VARCHAR(20) NOT NULL, 
	nombre 		VARCHAR(20) NOT NULL, 
	direccion 	VARCHAR(40) NOT NULL, 
	telefono 	VARCHAR(15) NOT NULL,

	CONSTRAINT pk_empleados
	PRIMARY KEY (legajo)

) ENGINE=InnoDB;


CREATE TABLE aeropuertos (
	codigo		VARCHAR(20) NOT NULL,
	nombre 		VARCHAR(40) NOT NULL,
	telefono 	VARCHAR(15) NOT NULL,
	direccion 	VARCHAR(30) NOT NULL,
	pais 		VARCHAR(20) NOT NULL,
	estado 		VARCHAR(20) NOT NULL,
	ciudad 		VARCHAR(20) NOT NULL,

	CONSTRAINT pk_ubicaciones
	PRIMARY KEY (codigo),
	
	CONSTRAINT pk_aeropuertos_ubicaciones
	FOREIGN KEY (pais, estado, ciudad) REFERENCES ubicaciones(pais, estado, ciudad)
	ON DELETE RESTRICT ON UPDATE RESTRICT

) ENGINE=InnoDB;


CREATE TABLE vuelos_programados (
	numero 				VARCHAR(10) NOT NULL,
	aeropuerto_salida 	VARCHAR(20) NOT NULL,
	aeropuerto_llegada 	VARCHAR(20) NOT NULL, 
	
	CONSTRAINT pk_vuelos_programados
	PRIMARY KEY (numero),
	
	CONSTRAINT pk_vuelos_programados_aeropuertos_salida
	FOREIGN KEY (aeropuerto_salida) REFERENCES aeropuertos(codigo)
	ON DELETE RESTRICT ON UPDATE RESTRICT,
	
	CONSTRAINT pk_vuelos_programados_aeropuertos_llegada
	FOREIGN KEY (aeropuerto_llegada) REFERENCES aeropuertos(codigo)
	ON DELETE RESTRICT ON UPDATE RESTRICT

) ENGINE=InnoDB;


CREATE TABLE salidas (
	vuelo 			VARCHAR(10) 								NOT NULL,
	dia 			ENUM ("Do","Lu","Ma","Mi","Ju","Vi","Sa") 	NOT NULL,
	hora_sale 		TIME 										NOT NULL,
	hora_llega 		TIME 										NOT NULL,
	modelo_avion 	VARCHAR(20) 								NOT NULL,
	
	CONSTRAINT pk_salidas
	PRIMARY KEY (vuelo, dia),
	
	CONSTRAINT pk_salidas_vuelo
	FOREIGN KEY (vuelo) REFERENCES vuelos_programados(numero)
	ON DELETE RESTRICT ON UPDATE RESTRICT,
	
	CONSTRAINT pk_salidas_modelo
	FOREIGN KEY (modelo_avion) REFERENCES modelos_avion(modelo)
	
	
) ENGINE=InnoDB;


CREATE TABLE instancias_vuelo (
	vuelo 	VARCHAR(10) 								NOT NULL,
	fecha 	DATE 										NOT NULL,
	dia 	ENUM ("Do","Lu","Ma","Mi","Ju","Vi","Sa") 	NOT NULL,
	estado 	VARCHAR(15),
	
	CONSTRAINT pk_instancias_vuelo
	PRIMARY KEY (vuelo, fecha),

	CONSTRAINT pk_instancias_vuelo_salidas
	FOREIGN KEY (vuelo, dia) REFERENCES salidas(vuelo, dia)

	
) ENGINE=InnoDB;


CREATE TABLE reservas (
	numero 		SMALLINT 	UNSIGNED AUTO_INCREMENT NOT NULL,
	fecha 		DATE 		NOT NULL,
	vencimiento DATE 		NOT NULL,
	estado 		VARCHAR(15) NOT NULL,
	doc_tipo 	VARCHAR(20) NOT NULL, 
	doc_nro 	SMALLINT 	UNSIGNED NOT NULL,
	legajo 		SMALLINT 	UNSIGNED NOT NULL,

	CONSTRAINT pk_reservas
	PRIMARY KEY (numero),

	CONSTRAINT pk_reservas_pasajeros
	FOREIGN KEY (doc_tipo, doc_nro) REFERENCES pasajeros(doc_tipo, doc_nro),
	
	CONSTRAINT pk_reservas_empleados
	FOREIGN KEY (legajo) REFERENCES empleados(legajo)	

) ENGINE=InnoDB;


CREATE TABLE brinda (
	vuelo 			VARCHAR(10) 								NOT NULL,
	dia 			ENUM ("Do","Lu","Ma","Mi","Ju","Vi","Sa") 	NOT NULL,
	clase 			VARCHAR(20) 								NOT NULL,
	precio 			DECIMAL(7, 2) 								UNSIGNED NOT NULL,
	cant_asientos 	SMALLINT 									UNSIGNED NOT NULL,
	
	CONSTRAINT pk_brinda
	PRIMARY KEY (vuelo, dia, clase),

	CONSTRAINT pk_brinda_salidas
	FOREIGN KEY (vuelo, dia) REFERENCES salidas(vuelo, dia),
	
	CONSTRAINT pk_brinda_clases
	FOREIGN KEY (clase) REFERENCES clases(nombre)	

) ENGINE=InnoDB;


CREATE TABLE posee (
	clase 		VARCHAR(20) NOT NULL,
	comodidad 	SMALLINT 	UNSIGNED NOT NULL,

	CONSTRAINT pk_posee
	PRIMARY KEY (clase, comodidad),

	CONSTRAINT pk_posee_clases
	FOREIGN KEY (clase) REFERENCES clases(nombre),
	
	CONSTRAINT pk_posee_comodidades
	FOREIGN KEY (comodidad) REFERENCES comodidades(codigo)	
	
) ENGINE=InnoDB;


CREATE TABLE reserva_vuelo_clase (
	numero 		SMALLINT 	UNSIGNED NOT NULL,
	vuelo 		VARCHAR(10) NOT NULL,
	fecha_vuelo DATE 		NOT NULL,
	clase 		VARCHAR(20) NOT NULL,
	
	CONSTRAINT pk_reserva_vuelo_clase
	PRIMARY KEY (numero, vuelo, fecha_vuelo),

	CONSTRAINT pk_reserva_vuelo_clase_reservas
	FOREIGN KEY (numero) REFERENCES reservas(numero),	

	CONSTRAINT pk_reserva_vuelo_clase_instacia_vuelo
	FOREIGN KEY (vuelo, fecha_vuelo) REFERENCES instancias_vuelo(vuelo, fecha),
	
	CONSTRAINT pk_reserva_vuelo_clase_clases
	FOREIGN KEY (clase) REFERENCES clases(nombre)

) ENGINE=InnoDB;


CREATE TABLE asientos_reservados (
	vuelo 		VARCHAR(10)	NOT NULL,
	fecha 		DATE 		NOT NULL,
	clase 		VARCHAR(20) NOT NULL,
	cantidad 	SMALLINT 	UNSIGNED NOT NULL,

	CONSTRAINT pk_asientos_reservados
	PRIMARY KEY (vuelo, fecha, clase),	

	CONSTRAINT pk_asientos_reservados_instacia_vuelo
	FOREIGN KEY (vuelo, fecha) REFERENCES instancias_vuelo(vuelo, fecha),
	
	CONSTRAINT pk_asientos_reservados_clases
	FOREIGN KEY (clase) REFERENCES clases(nombre)
		
) ENGINE=InnoDB;


/************************************************************************/
/************************** CREACION DE VISTAS **************************/
/************************************************************************/

CREATE VIEW vuelos_disponibles AS 

	SELECT 	numero AS nro_vuelo,
			modelo_avion AS modelo,
			fecha,
			dia AS dia_sale,
			hora_sale,
			hora_llega,
			timediff(hora_llega, hora_sale) AS tiempo_estimado,
			salida.codigo AS codigo_aero_sale,
			salida.nombre AS nombre_aero_sale,
			salida.ciudad AS ciudad_sale,
			salida.estado AS estado_sale,
			salida.pais AS pais_sale,
			llegada.codigo AS codigo_aero_llega,
			llegada.nombre AS nombre_aero_llega,
			llegada.ciudad AS ciudad_llega,
			llegada.estado AS estado_llega,
			llegada.pais AS pais_llega,
			precio,
			(round(cant_asientos + cant_asientos * porcentaje) - cantidad) AS asientos_disponibles,
			clase
	FROM
			vuelos_programados
			JOIN salidas ON numero = vuelo
			NATURAL LEFT JOIN instancias_vuelo
			NATURAL LEFT JOIN brinda
			JOIN clases ON clase = nombre
			NATURAL LEFT JOIN asientos_reservados
			JOIN aeropuertos AS salida ON aeropuerto_salida = salida.codigo
			JOIN aeropuertos AS llegada On aeropuerto_llegada = llegada.codigo
	ORDER BY
			nro_vuelo ASC;

/*
	SELECT 	nro_vuelo,
			modelo,
			fecha,
			dia_sale,
			hora_sale,
			hora_llega,
			tiempo_estimado,
			codigo_aero_sale,
			nombre_aero_sale,
			ciudad_sale,
			estado_sale,
			pais_sale,
			codigo_aero_llega,
			nombre_aero_llega,
			ciudad_llega,
			estado_llega,
			pais_llega,
			precio,
			asientos_disponibles,
			clase
	FROM
		(SELECT	numero AS nro_vuelo,
				codigo AS codigo_aero_sale,
				nombre AS nombre_aero_sale,
				ciudad AS ciudad_sale,
				estado AS estado_sale,
				pais AS pais_sale
		FROM
			(SELECT	numero,
					aeropuerto_salida
			FROM vuelos_programados) VS
		JOIN aeropuertos ON VS.aeropuerto_salida = codigo) VS
	NATURAL JOIN
		(SELECT numero AS nro_vuelo,
				codigo AS codigo_aero_llega,
				nombre AS nombre_aero_llega,
				ciudad AS ciudad_llega,
				estado AS estado_llega,
				pais AS pais_llega
		FROM
			(SELECT numero,
					aeropuerto_llegada
			FROM vuelos_programados) VS
		JOIN aeropuertos ON VS.aeropuerto_llegada = codigo) VL
	NATURAL JOIN
		(SELECT numero AS nro_vuelo,
				modelo_avion AS modelo,
				fecha,
				dia AS dia_sale,
				hora_sale,
				hora_llega,
				concat(hora_llega - hora_sale) AS tiempo_estimado
		FROM
			(SELECT *
			FROM salidas
			NATURAL JOIN instancias_vuelo) S
		JOIN vuelos_programados ON numero = S.vuelo) VM
	NATURAL JOIN
		(SELECT brinda.vuelo AS nro_vuelo, 
		 		brinda.clase,
				precio,
				concat(cant_asientos - cantidad) AS asientos_disponibles
		FROM brinda
		JOIN asientos_reservados ON brinda.vuelo = asientos_reservados.vuelo AND brinda.clase = asientos_reservados.clase) VP;
*/


/****************************************************************************/
/************************** DEFINICION DE USUARIOS **************************/
/****************************************************************************/

CREATE USER 'admin'@'localhost' IDENTIFIED BY 'admin';
GRANT ALL PRIVILEGES ON vuelos.* TO 'admin'@'localhost' WITH GRANT OPTION;

CREATE USER 'empleado'@'%' IDENTIFIED BY 'empleado';
GRANT SELECT ON vuelos.* TO 'empleado'@'%';

GRANT ALL PRIVILEGES ON vuelos.reservas TO 'empleado'@'%' WITH GRANT OPTION;
GRANT ALL PRIVILEGES ON vuelos.pasajeros TO 'empleado'@'%' WITH GRANT OPTION;
GRANT ALL PRIVILEGES ON vuelos.reserva_vuelo_clase TO 'empleado'@'%' WITH GRANT OPTION;

CREATE USER 'cliente'@'%' IDENTIFIED BY 'cliente'; 
GRANT SELECT ON vuelos.vuelos_disponibles TO 'cliente'@'%';

DROP USER ''@'localhost';
