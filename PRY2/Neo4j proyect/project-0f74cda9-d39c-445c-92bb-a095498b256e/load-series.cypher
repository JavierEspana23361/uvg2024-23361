// Creación de nodos para los géneros de las series
CREATE (Comedia:Genero {nombre:'Comedia'})
CREATE (Terror:Genero {nombre:'Terror'})
CREATE (Drama:Genero {nombre:'Drama'})
CREATE (Misterio:Genero {nombre:'Misterio'})


//Actores
CREATE (Millie:Person {name:'Millie Bobby Brown', born:2004})
CREATE (Finn:Person {name:'Finn Wolfhard', born:2002})
CREATE (David:Person {name:'David Harbour', born:1975})
CREATE (Winona:Person {name:'Winona Ryder', born:1971})
CREATE (Olivia:Person {name:'Olivia Colman', born:1974})
CREATE (Tobias:Person {name:'Tobias Menzies', born:1974})
CREATE (Helena:Person {name:'Helena Bonham Carter', born:1966})
CREATE (Louis:Person {name:'Louis Hofmann', born:1997})
CREATE (Lisa:Person {name:'Lisa Vicari', born:1997})


//Directores/Creadores
CREATE (DufferBros:Person {name:'Duffer Brothers', born:1984})
CREATE (ShawnLevy:Person {name:'Shawn Levy', born:1968})
CREATE (Charlie:Person {name:'Charlie Brooker', born:1971})
CREATE (Peter:Person {name:'Peter Morgan', born:1963})
CREATE (Jantje:Person {name:'Jantje Friese', born:1977})
CREATE (Baran:Person {name:'Baran bo Odar', born:1978})


//Series
CREATE (StrangerThings:Series {title:'Stranger Things', released:2016, tagline:'Upside Down'})
CREATE (StrangerThings)-[:PERTENECE_A]->(Drama)
CREATE (StrangerThings)-[:ACTED_IN {roles:['Eleven']}]->(Millie),
       (StrangerThings)-[:ACTED_IN {roles:['Mike']}]->(Finn),
       (StrangerThings)-[:ACTED_IN {roles:['Hopper']}]->(David),
       (StrangerThings)-[:ACTED_IN {roles:['Joyce']}]->(Winona),
       (StrangerThings)-[:DIRECTED]->(DufferBros),
       (StrangerThings)-[:DIRECTED]->(ShawnLevy)


CREATE (BlackMirror:Series {title:'Black Mirror', released:2011, tagline:'Technology Nightmare'})
CREATE (BlackMirror)-[:PERTENECE_A]->(Misterio)
CREATE (BlackMirror)-[:CREATED_BY]->(Charlie)




CREATE (TheCrown:Series {title:'The Crown', released:2016, tagline:'Royal Drama'})
CREATE (TheCrown)-[:PERTENECE_A]->(Drama)
CREATE (TheCrown)-[:ACTED_IN {roles:['Queen Elizabeth II']}]->(Olivia),
       (TheCrown)-[:ACTED_IN {roles:['Prince Philip']}]->(Tobias),
       (TheCrown)-[:ACTED_IN {roles:['Princess Margaret']}]->(Helena),
       (TheCrown)-[:CREATED_BY]->(Peter)

CREATE (Dark:Series {title:'Dark', released:2017, tagline:'Time Travel Mystery'})
CREATE (Dark)-[:PERTENECE_A]->(Misterio)
CREATE (Dark)-[:ACTED_IN {roles:['Jonas']}]->(Louis),
       (Dark)-[:ACTED_IN {roles:['Martha']}]->(Lisa),
       (Dark)-[:CREATED_BY]->(Baran),
       (Dark)-[:CREATED_BY]->(Jantje)
;