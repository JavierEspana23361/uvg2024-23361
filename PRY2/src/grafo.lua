CREATE (Comedia:Genero {nombre:'Comedia'})
CREATE (Terror:Genero {nombre:'Terror'})
CREATE (Drama:Genero {nombre:'Drama'})
CREATE (Misterio:Genero {nombre:'Misterio'})
CREATE (Accion: Genero {nombre: 'Accion'})
CREATE (CienciaFiccion: Genero {nombre: 'Ciencia Ficcion'})
CREATE (Fantasia: Genero {nombre: 'Fantasia'})
CREATE (Aventura: Genero {nombre: 'Aventura'})
CREATE (Documental: Genero {nombre: 'Documental'})
CREATE (Musical: Genero {nombre: 'Musical'})
CREATE (Romance: Genero {nombre: 'Romance'})
CREATE (Suspenso: Genero {nombre: 'Suspenso'})
CREATE (Animacion: Genero {nombre: 'Animacion'})
CREATE (Biografia: Genero {nombre: 'Biografia'})
CREATE (Crimen: Genero {nombre: 'Crimen'})
CREATE (Familia: Genero {nombre: 'Familia'})
CREATE (Guerra: Genero {nombre: 'Guerra'})
CREATE (Historia: Genero {nombre: 'Historia'})
CREATE (Deporte: Genero {nombre: 'Deporte'})
CREATE (Politica: Genero {nombre: 'Politica'})
CREATE (Superheroes: Genero {nombre: 'Superheroes'})
CREATE (Thriller: Genero {nombre: 'Thriller'})
CREATE (Western: Genero {nombre: 'Western'})
CREATE (Horror: Genero {nombre: 'Horror'})

CREATE (StrangerThings:Series {title:'Stranger Things', released:2016, tagline:'Upside Down'})
CREATE (StrangerThings)-[:PERTENECE_A]->(Drama)

CREATE (BlackMirror:Series {title:'Black Mirror', released:2011, tagline:'Technology Nightmare'})
CREATE (BlackMirror)-[:PERTENECE_A]->(Misterio)

CREATE (TheCrown:Series {title:'The Crown', released:2016, tagline:'Royal Drama'})
CREATE (TheCrown)-[:PERTENECE_A]->(Drama)

CREATE (Dark:Series {title:'Dark', released:2017, tagline:'Time Travel Mystery'})
CREATE (Dark)-[:PERTENECE_A]->(Misterio)

CREATE (GameOfThrones:Series {title:'Game of Thrones', released:2011, tagline:'Winter is Coming'})
CREATE (GameOfThrones)-[:PERTENECE_A]->(Aventura)
CREATE (GameOfThrones)-[:PERTENECE_A]->(Fantasia)

CREATE (TheWitcher:Series {title:'The Witcher', released:2019, tagline:'Monster Hunter'})
CREATE (TheWitcher)-[:PERTENECE_A]->(Aventura)
CREATE (TheWitcher)-[:PERTENECE_A]->(Fantasia)

CREATE (TheMandalorian:Series {title:'The Mandalorian', released:2019, tagline:'Bounty Hunter'})
CREATE (TheMandalorian)-[:PERTENECE_A]->(Accion)
CREATE (TheMandalorian)-[:PERTENECE_A]->(CienciaFiccion)

CREATE (Ozark:Series {title:'Ozark', released:2017, tagline:'Money Laundering'})
CREATE (Ozark)-[:PERTENECE_A]->(Crimen)
CREATE (Ozark)-[:PERTENECE_A]->(Drama)

CREATE (TheUmbrellaAcademy:Series {title:'The Umbrella Academy', released:2019, tagline:'Superhero Family'})
CREATE (TheUmbrellaAcademy)-[:PERTENECE_A]->(Accion)
CREATE (TheUmbrellaAcademy)-[:PERTENECE_A]->(Comedia)

CREATE (TheHauntingOfHillHouse:Series {title:'The Haunting of Hill House', released:2018, tagline:'Haunted House'})
CREATE (TheHauntingOfHillHouse)-[:PERTENECE_A]->(Terror)
CREATE (TheHauntingOfHillHouse)-[:PERTENECE_A]->(Drama)

CREATE (BreakingBad:Series {title:'Breaking Bad', released:2008, tagline:'Meth King'})
CREATE (BreakingBad)-[:PERTENECE_A]->(Crimen)
CREATE (BreakingBad)-[:PERTENECE_A]->(Drama)

CREATE (BetterCallSaul:Series {title:'Better Call Saul', released:2015, tagline:'Lawyer Drama'})
CREATE (BetterCallSaul)-[:PERTENECE_A]->(Crimen)
CREATE (BetterCallSaul)-[:PERTENECE_A]->(Drama)

//Primera subida de datos
