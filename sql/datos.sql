INSERT INTO ubicaciones VALUES ("Argentina", "Buenos Aires", "Bahia Blanca", -3);
INSERT INTO ubicaciones VALUES ( "Argentina","Buenos Aires", "Ezeiza", -3);
INSERT INTO ubicaciones VALUES ("Argentina", "Cordoba", "Cordoba", -3);
INSERT INTO ubicaciones VALUES ( "Brasil", "Sao Paulo", "Sao Paulo", -3);
INSERT INTO ubicaciones VALUES ("Espana", "Madrid", "Madrid", 2);
INSERT INTO ubicaciones VALUES ( "Estados Unidos","Florida", "Miami", -4);


INSERT INTO aeropuertos VALUES ("A-001", "Comandante Espora", 0291000, "Ruta-3 Vieja km 675", "Argentina", "Buenos Aires", "Bahia Blanca");
INSERT INTO aeropuertos VALUES ("A-002", "Ministro Pistarini", 0110000, "Pablo Riccheri Km 33,5", "Argentina","Buenos Aires", "Ezeiza");
INSERT INTO aeropuertos VALUES ("A-003", "Ingeniero Aeronautico Ambrosio Taravella", 0351000, "Av. La Voz del Interior 8500", "Argentina", "Cordoba", "Cordoba");
INSERT INTO aeropuertos VALUES ("A-301", "Governador Andre Franco Montoro", 5511000, "Rod. Hélio Smidt", "Brasil", "Sao Paulo", "Sao Paulo");
INSERT INTO aeropuertos VALUES ("A-501", " Adolfo Suárez Madrid-Barajas", 3491300, "Av de la Hispanidad", "Espana", "Madrid", "Madrid");
INSERT INTO aeropuertos VALUES ("A-202", "Aeropuerto Internacional de Miami", 0130500, "2100 NW 42nd Ave", "Estados Unidos","Florida", "Miami");


INSERT INTO modelos_avion VALUES ("Boeing-737-800", "Boeing", 1, 189);
INSERT INTO modelos_avion VALUES ("Airbus A330-200", "Airbus", 1, 272);
INSERT INTO modelos_avion VALUES ("Boeing 787", "Boeing", 1, 285);


INSERT INTO vuelos_programados VALUES ("0001", "A-001", "A-002");
INSERT INTO vuelos_programados VALUES ("0002", "A-002", "A-003");
INSERT INTO vuelos_programados VALUES ("3001", "A-002", "A-301");
INSERT INTO vuelos_programados VALUES ("5001", "A-501", "A-002");
INSERT INTO vuelos_programados VALUES ("5003", "A-202", "A-002");
INSERT INTO vuelos_programados VALUES ("2000", "A-002", "A-202");
INSERT INTO vuelos_programados VALUES ("2011", "A-501", "A-202");


INSERT INTO salidas VALUES ("0001", "Do", 00,  01, "Boeing-737-800");
INSERT INTO salidas VALUES ("0002", "Vi", 07,  10, "Airbus A330-200");
INSERT INTO salidas VALUES ("3001", "Vi", 12,  20, "Airbus A330-200");
INSERT INTO salidas VALUES ("5001", "Lu", 09,  23, "Boeing 787");
INSERT INTO salidas VALUES ("5003", "Sa", 11,  21, "Boeing 787");
INSERT INTO salidas VALUES ("2000", "Mi", 12,  22, "Boeing 787");
INSERT INTO salidas VALUES ("2011", "Ju", 08,  19, "Boeing 787");


INSERT INTO instancias_vuelo VALUES ("0001", "2022-09-20", "Do", "Pendiente");
INSERT INTO instancias_vuelo VALUES ("0002", "2022-09-18", "Vi", "Pendiente");
INSERT INTO instancias_vuelo VALUES ("3001", "2022-10-7", "Vi", "Pendiente");
INSERT INTO instancias_vuelo VALUES ("5001", "2022-10-10", "Lu", "Pendiente");
INSERT INTO instancias_vuelo VALUES ("5003", "2022-10-20", "Sa", "Pendiente");
INSERT INTO instancias_vuelo VALUES ("2000", "2022-10-12", "Mi", "Pendiente");
INSERT INTO instancias_vuelo VALUES ("2011", "2022-10-13", "Ju", "Pendiente");


INSERT INTO clases VALUES ("Turista", 0.75);
INSERT INTO clases VALUES ("Ejecutivo", 0.25);


INSERT INTO comodidades VALUES (01, "Simple");
INSERT INTO comodidades VALUES (02, "Muy comodo, privado");


INSERT INTO posee VALUES ("Turista", 01);
INSERT INTO posee VALUES ("Ejecutivo", 02);


INSERT INTO brinda VALUES ("0001", "Do", "Turista", 100.25, 149);
INSERT INTO brinda VALUES ("0001", "Do", "Ejecutivo", 325.50, 40);

INSERT INTO brinda VALUES ("0002", "Vi", "Turista", 150.00, 200);
INSERT INTO brinda VALUES ("0002", "Vi", "Ejecutivo", 400.25, 72);

INSERT INTO brinda VALUES ("3001", "Vi", "Turista", 170.25, 200);
INSERT INTO brinda VALUES ("3001", "Vi", "Ejecutivo", 350.50, 72);

INSERT INTO brinda VALUES ("5001", "Lu", "Turista", 200.25, 215);
INSERT INTO brinda VALUES ("5001", "Lu", "Ejecutivo", 425.50, 70);

INSERT INTO brinda VALUES ("5003", "Sa", "Turista", 185.25, 215);
INSERT INTO brinda VALUES ("5003", "Sa", "Ejecutivo", 375.50, 70);

INSERT INTO brinda VALUES ("2000", "Mi", "Turista", 210.25, 215);
INSERT INTO brinda VALUES ("2000", "Mi", "Ejecutivo", 400.50, 70);

INSERT INTO brinda VALUES ("2011", "Ju", "Turista", 200.00, 149);
INSERT INTO brinda VALUES ("2011", "Ju", "Ejecutivo", 478.20, 40);


INSERT INTO asientos_reservados VALUES ("0001", "2022-09-20", "Turista", 75);
INSERT INTO asientos_reservados VALUES ("0001", "2022-09-20", "Ejecutivo", 30);

INSERT INTO asientos_reservados VALUES ("0002", "2022-09-18", "Turista", 150);
INSERT INTO asientos_reservados VALUES ("0002", "2022-09-18", "Ejecutivo", 50);

INSERT INTO asientos_reservados VALUES ("3001", "2022-10-7", "Turista", 90);
INSERT INTO asientos_reservados VALUES ("3001", "2022-10-7", "Ejecutivo", 30);

INSERT INTO asientos_reservados VALUES ("5001", "2022-10-10", "Turista", 60);
INSERT INTO asientos_reservados VALUES ("5001", "2022-10-10", "Ejecutivo", 10);

INSERT INTO asientos_reservados VALUES ("5003", "2022-10-20", "Turista", 25);
INSERT INTO asientos_reservados VALUES ("5003", "2022-10-20", "Ejecutivo", 0);

INSERT INTO asientos_reservados VALUES ("2000", "2022-10-12", "Turista", 67);
INSERT INTO asientos_reservados VALUES ("2000", "2022-10-12", "Ejecutivo", 23);

INSERT INTO asientos_reservados VALUES ("2011", "2022-10-13", "Turista", 100);
INSERT INTO asientos_reservados VALUES ("2011", "2022-10-13", "Ejecutivo", 40);


INSERT INTO empleados VALUES (1200, MD5("empleado1"), "dni", 456, "Diaz", "Pedro", "Alberdi 100", "291011");
INSERT INTO empleados VALUES (1223, MD5("empleado2"), "dni", 351, "Gomez", "Raul", "Alem 1250", "291258");


INSERT INTO pasajeros VALUES ("dni", 256, "Perez", "Marcelo", "Rivadavia 12", "2910121", "Argentina");
INSERT INTO pasajeros VALUES ("dni", 471, "Garcia", "Rocio", "Rivadavia 12", "2910121", "Argentina");
INSERT INTO pasajeros VALUES ("dni", 365, "Garcia", "Tadeo", "Rivadavia 12", "2910121", "Argentina");
INSERT INTO pasajeros VALUES ("dni", 278, "Rodriguez", "Luciano", "Rivadavia 12", "2910121", "Argentina");


INSERT INTO reservas VALUES (01, "2022-08-20", "2022-09-12", "Pago", "dni", 256, 1200);
INSERT INTO reservas VALUES (02, "2022-09-15", "2022-10-10", "Impago", "dni", 471, 1223);
INSERT INTO reservas VALUES (03, "2022-09-15", "2022-10-10", "Impago", "dni", 365, 1223);
INSERT INTO reservas VALUES (04, "2022-08-10", "2022-10-01", "Pago", "dni", 256, 1200);


INSERT INTO reserva_vuelo_clase VALUES (01, "0001", "2022-09-20", "Turista");
INSERT INTO reserva_vuelo_clase VALUES (02, "2000", "2022-10-12", "Ejecutivo");
INSERT INTO reserva_vuelo_clase VALUES (03, "2000", "2022-10-12", "Turista");
INSERT INTO reserva_vuelo_clase VALUES (04, "5001", "2022-10-10", "Ejecutivo");