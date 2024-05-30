//Primera subida de datos

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
MATCH (s:Series {title:'Stranger Things'}), (g:Genero {nombre:'Drama'})
CREATE (s)-[:PERTENECE_A]->(g)


CREATE (BlackMirror:Series {title:'Black Mirror', released:2011, tagline:'Technology Nightmare'})
MATCH (s:Series {title:'Black Mirror'}), (g:Genero {nombre:'Ciencia Ficcion'})
CREATE (s)-[:PERTENECE_A]->(g)

CREATE (TheCrown:Series {title:'The Crown', released:2016, tagline:'Royal Drama'})
MATCH (s:Series {title:'The Crown'}), (g:Genero {nombre:'Historia'})
CREATE (s)-[:PERTENECE_A]->(g)
MATCH (s:Series {title:'The Crown'}), (g:Genero {nombre:'Drama'})
CREATE (s)-[:PERTENECE_A]->(g)

CREATE (Dark:Series {title:'Dark', released:2017, tagline:'Time Travel Mystery'})
MATCH (s:Series {title:'Dark'}), (g:Genero {nombre:'Misterio'})
CREATE (s)-[:PERTENECE_A]->(g)
MATCH (s:Series {title:'Dark'}), (g:Genero {nombre:'Ciencia Ficcion'})
CREATE (s)-[:PERTENECE_A]->(g)

CREATE (GameOfThrones:Series {title:'Game of Thrones', released:2011, tagline:'Winter is Coming'})
MATCH (s:Series {title:'Game of Thrones'}), (g:Genero {nombre:'Fantasia'})
CREATE (s)-[:PERTENECE_A]->(g)
MATCH (s:Series {title:'Game of Thrones'}), (g:Genero {nombre:'Aventura'})
CREATE (s)-[:PERTENECE_A]->(g)


CREATE (TheWitcher:Series {title:'The Witcher', released:2019, tagline:'Monster Hunter'})
MATCH (s:Series {title:'The Witcher'}), (g:Genero {nombre:'Fantasia'})
CREATE (s)-[:PERTENECE_A]->(g)
MATCH (s:Series {title:'The Witcher'}), (g:Genero {nombre:'Accion'})
CREATE (s)-[:PERTENECE_A]->(g)

CREATE (TheMandalorian:Series {title:'The Mandalorian', released:2019, tagline:'Bounty Hunter'})
MATCH (s:Series {title:'The Mandalorian'}), (g:Genero {nombre:'Accion'})
CREATE (s)-[:PERTENECE_A]->(g)
MATCH (s:Series {title:'The Mandalorian'}), (g:Genero {nombre:'Ciencia Ficcion'})
CREATE (s)-[:PERTENECE_A]->(g)

CREATE (Ozark:Series {title:'Ozark', released:2017, tagline:'Money Laundering'})
MATCH (s:Series {title:'Ozark'}), (g:Genero {nombre:'Crimen'})
CREATE (s)-[:PERTENECE_A]->(g)
MATCH (s:Series {title:'Ozark'}), (g:Genero {nombre:'Drama'})
CREATE (s)-[:PERTENECE_A]->(g)

CREATE (TheUmbrellaAcademy:Series {title:'The Umbrella Academy', released:2019, tagline:'Superhero Family'})
MATCH (s:Series {title:'The Umbrella Academy'}), (g:Genero {nombre:'Accion'})
CREATE (s)-[:PERTENECE_A]->(g)
MATCH (s:Series {title:'The Umbrella Academy'}), (g:Genero {nombre:'Superheroes'})
CREATE (s)-[:PERTENECE_A]->(g)


CREATE (TheHauntingOfHillHouse:Series {title:'The Haunting of Hill House', released:2018, tagline:'Haunted House'})
MATCH (s:Series {title:'The Haunting of Hill House'}), (g:Genero {nombre:'Horror'})
CREATE (s)-[:PERTENECE_A]->(g)
MATCH (s:Series {title:'The Haunting of Hill House'}), (g:Genero {nombre:'Drama'})
CREATE (s)-[:PERTENECE_A]->(g)


CREATE (BreakingBad:Series {title:'Breaking Bad', released:2008, tagline:'Meth King'})
MATCH (s:Series {title:'Breaking Bad'}), (g:Genero {nombre:'Crimen'})
CREATE (s)-[:PERTENECE_A]->(g)
MATCH (s:Series {title:'Breaking Bad'}), (g:Genero {nombre:'Drama'})
CREATE (s)-[:PERTENECE_A]->(g)


CREATE (BetterCallSaul:Series {title:'Better Call Saul', released:2015, tagline:'Lawyer Drama'})
MATCH (s:Series {title:'Better Call Saul'}), (g:Genero {nombre:'Crimen'})
CREATE (s)-[:PERTENECE_A]->(g)
MATCH (s:Series {title:'Better Call Saul'}), (g:Genero {nombre:'Drama'})
CREATE (s)-[:PERTENECE_A]->(g)


//me quedé aquí



CREATE (Narcos:Series {title:'Narcos', released:2015, tagline:'Drug Cartel'})
MATCH (s:Series {title:'Narcos'}), (g:Genero {nombre:'Crimen'})
CREATE (s)-[:PERTENECE_A]->(g)
MATCH (s:Series {title:'Narcos'}), (g:Genero {nombre:'Drama'})
CREATE (s)-[:PERTENECE_A]->(g)

CREATE (MoneyHeist:Series {title:'Money Heist', released:2017, tagline:'Bank Robbery'})
MATCH (s:Series {title:'Money Heist'}), (g:Genero {nombre:'Crimen'})
CREATE (s)-[:PERTENECE_A]->(g)
MATCH (s:Series {title:'Money Heist'}), (g:Genero {nombre:'Drama'})
CREATE (s)-[:PERTENECE_A]->(g)

CREATE (TheBoys:Series {title:'The Boys', released:2019, tagline:'Superhero Satire'})
MATCH (s:Series {title:'The Boys'}), (g:Genero {nombre:'Accion'})
CREATE (s)-[:PERTENECE_A]->(g)
MATCH (s:Series {title:'The Boys'}), (g:Genero {nombre:'Superheroes'})
CREATE (s)-[:PERTENECE_A]->(g)



CREATE (TheWalkingDead:Series {title:'The Walking Dead', released:2010, tagline:'Zombie Apocalypse'})
MATCH (s:Series {title:'The Walking Dead'}), (g:Genero {nombre:'Accion'})
CREATE (s)-[:PERTENECE_A]->(g)
MATCH (s:Series {title:'The Walking Dead'}), (g:Genero {nombre:'Drama'})
CREATE (s)-[:PERTENECE_A]->(g)
MATCH (s:Series {title:'The Walking Dead'}), (g:Genero {nombre:'Horror'})
CREATE (s)-[:PERTENECE_A]->(g)

CREATE (TheOffice:Series {title:'The Office', released:2005, tagline:'Office Comedy'})
MATCH (s:Series {title:'The Office'}), (g:Genero {nombre:'Comedia'})
CREATE (s)-[:PERTENECE_A]->(g)



CREATE (Friends:Series {title:'Friends', released:1994, tagline:'Friendship Comedy'})
MATCH (s:Series {title:'Friends'}), (g:Genero {nombre:'Comedia'})
CREATE (s)-[:PERTENECE_A]->(g)
MATCH (s:Series {title:'Friends'}), (g:Genero {nombre:'Romance'})
CREATE (s)-[:PERTENECE_A]->(g)

CREATE (TheBigBangTheory:Series {title:'The Big Bang Theory', released:2007, tagline:'Nerd Comedy'})
MATCH (s:Series {title:'The Big Bang Theory'}), (g:Genero {nombre:'Comedia'})
CREATE (s)-[:PERTENECE_A]->(g)

CREATE (HowIMetYourMother:Series {title:'How I Met Your Mother', released:2005, tagline:'Love Story'})
MATCH (s:Series {title:'How I Met Your Mother'}), (g:Genero {nombre:'Comedia'})
CREATE (s)-[:PERTENECE_A]->(g)
MATCH (s:Series {title:'How I Met Your Mother'}), (g:Genero {nombre:'Romance'})
CREATE (s)-[:PERTENECE_A]->(g)


CREATE (TheSimpsons:Series {title:'The Simpsons', released:1989, tagline:'Family Comedy'})
MATCH (s:Series {title:'The Simpsons'}), (g:Genero {nombre:'Comedia'})
CREATE (s)-[:PERTENECE_A]->(g)
MATCH (s:Series {title:'The Simpsons'}), (g:Genero {nombre:'Animacion'})
CREATE (s)-[:PERTENECE_A]->(g)

CREATE (FamilyGuy:Series {title:'Family Guy', released:1999, tagline:'Animated Comedy'})
MATCH (s:Series {title:'Family Guy'}), (g:Genero {nombre:'Comedia'})
CREATE (s)-[:PERTENECE_A]->(g)
MATCH (s:Series {title:'Family Guy'}), (g:Genero {nombre:'Animacion'})
CREATE (s)-[:PERTENECE_A]->(g)

CREATE (SouthPark:Series {title:'South Park', released:1997, tagline:'Adult Comedy'})
MATCH (s:Series {title:'South Park'}), (g:Genero {nombre:'Comedia'})
CREATE (s)-[:PERTENECE_A]->(g)
MATCH (s:Series {title:'South Park'}), (g:Genero {nombre:'Animacion'})
CREATE (s)-[:PERTENECE_A]->(g)

CREATE (TheSopranos:Series {title:'The Sopranos', released:1999, tagline:'Mafia Drama'})
MATCH (s:Series {title:'The Sopranos'}), (g:Genero {nombre:'Crimen'})
CREATE (s)-[:PERTENECE_A]->(g)
MATCH (s:Series {title:'The Sopranos'}), (g:Genero {nombre:'Drama'})
CREATE (s)-[:PERTENECE_A]->(g)

CREATE (TheWire:Series {title:'The Wire', released:2002, tagline:'Baltimore Crime'})
MATCH (s:Series {title:'The Wire'}), (g:Genero {nombre:'Crimen'})
CREATE (s)-[:PERTENECE_A]->(g)
MATCH (s:Series {title:'The Wire'}), (g:Genero {nombre:'Drama'})
CREATE (s)-[:PERTENECE_A]->(g)

CREATE (TheMentalist:Series {title:'The Mentalist', released:2008, tagline:'Psychic Detective'})
MATCH (s:Series {title:'The Mentalist'}), (g:Genero {nombre:'Crimen'})
CREATE (s)-[:PERTENECE_A]->(g)
MATCH (s:Series {title:'The Mentalist'}), (g:Genero {nombre:'Drama'})
CREATE (s)-[:PERTENECE_A]->(g)

CREATE (Sherlock:Series {title:'Sherlock', released:2010, tagline:'Modern Sherlock Holmes'})
MATCH (s:Series {title:'Sherlock'}), (g:Genero {nombre:'Misterio'})
CREATE (s)-[:PERTENECE_A]->(g)
MATCH (s:Series {title:'Sherlock'}), (g:Genero {nombre:'Drama'})
CREATE (s)-[:PERTENECE_A]->(g)

CREATE (TheBlacklist:Series {title:'The Blacklist', released:2013, tagline:'Criminal Mastermind'})
MATCH (s:Series {title:'The Blacklist'}), (g:Genero {nombre:'Crimen'})
CREATE (s)-[:PERTENECE_A]->(g)
MATCH (s:Series {title:'The Blacklist'}), (g:Genero {nombre:'Drama'})
CREATE (s)-[:PERTENECE_A]->(g)

CREATE (TheGoodDoctor:Series {title:'The Good Doctor', released:2017, tagline:'Autistic Doctor'})
MATCH (s:Series {title:'The Good Doctor'}), (g:Genero {nombre:'Drama'})
CREATE (s)-[:PERTENECE_A]->(g)

CREATE (House:Series {title:'House', released:2004, tagline:'Medical Drama'})
MATCH (s:Series {title:'House'}), (g:Genero {nombre:'Drama'})
CREATE (s)-[:PERTENECE_A]->(g)


CREATE (GreyAnatomy:Series {title:'Grey Anatomy', released:2005, tagline:'Medical Drama'})
MATCH (s:Series {title:'Grey Anatomy'}), (g:Genero {nombre:'Drama'})
CREATE (s)-[:PERTENECE_A]->(g)
MATCH (s:Series {title:'Grey Anatomy'}), (g:Genero {nombre:'Romance'})
CREATE (s)-[:PERTENECE_A]->(g)

CREATE (TheGoodWife:Series {title:'The Good Wife', released:2009, tagline:'Lawyer Drama'})
MATCH (s:Series {title:'The Good Wife'}), (g:Genero {nombre:'Drama'})
CREATE (s)-[:PERTENECE_A]->(g)

CREATE (Suits:Series {title:'Suits', released:2011, tagline:'Lawyer Drama'})
MATCH (s:Series {title:'Suits'}), (g:Genero {nombre:'Drama'})
CREATE (s)-[:PERTENECE_A]->(g)

CREATE (Vikings:Series {title:'Vikings', released:2013, tagline:'Norse Warriors'})
MATCH (s:Series {title:'Vikings'}), (g:Genero {nombre:'Accion'})
CREATE (s)-[:PERTENECE_A]->(g)
MATCH (s:Series {title:'Vikings'}), (g:Genero {nombre:'Drama'})

CREATE (PeakyBlinders:Series {title:'Peaky Blinders', released:2013, tagline:'Gangster Drama'})
MATCH (s:Series {title:'Peaky Blinders'}), (g:Genero {nombre:'Crimen'})
CREATE (s)-[:PERTENECE_A]->(g)
MATCH (s:Series {title:'Peaky Blinders'}), (g:Genero {nombre:'Drama'})
CREATE (s)-[:PERTENECE_A]->(g)

CREATE (TheLastKingdom:Series {title:'The Last Kingdom', released:2015, tagline:'Saxon Warrior'})
MATCH (s:Series {title:'The Last Kingdom'}), (g:Genero {nombre:'Accion'})
CREATE (s)-[:PERTENECE_A]->(g)
MATCH (s:Series {title:'The Last Kingdom'}), (g:Genero {nombre:'Drama'})
CREATE (s)-[:PERTENECE_A]->(g)

CREATE (TheVampireDiaries:Series {title:'The Vampire Diaries', released:2009, tagline:'Vampire Romance'})
MATCH (s:Series {title:'The Vampire Diaries'}), (g:Genero {nombre:'Drama'})
CREATE (s)-[:PERTENECE_A]->(g)
MATCH (s:Series {title:'The Vampire Diaries'}), (g:Genero {nombre:'Fantasia'})
CREATE (s)-[:PERTENECE_A]->(g)


CREATE (TheOriginals:Series {title:'The Originals', released:2013, tagline:'Vampire Family'})
MATCH (s:Series {title:'The Originals'}), (g:Genero {nombre:'Drama'})
CREATE (s)-[:PERTENECE_A]->(g)
MATCH (s:Series {title:'The Originals'}), (g:Genero {nombre:'Fantasia'})
CREATE (s)-[:PERTENECE_A]->(g)

CREATE (Supernatural:Series {title:'Supernatural', released:2005, tagline:'Monster Hunters'})
MATCH (s:Series {title:'Supernatural'}), (g:Genero {nombre:'Accion'})
CREATE (s)-[:PERTENECE_A]->(g)
MATCH (s:Series {title:'Supernatural'}), (g:Genero {nombre:'Fantasia'})
CREATE (s)-[:PERTENECE_A]->(g)


CREATE (The100:Series {title:'The 100', released:2014, tagline:'Post-Apocalyptic Survival'})
MATCH (s:Series {title:'The 100'}), (g:Genero {nombre:'Accion'})
CREATE (s)-[:PERTENECE_A]->(g)
MATCH (s:Series {title:'The 100'}), (g:Genero {nombre:'Drama'})
CREATE (s)-[:PERTENECE_A]->(g)


CREATE (TheFlash:Series {title:'The Flash', released:2014, tagline:'Speedster Hero'})
MATCH (s:Series {title:'The Flash'}), (g:Genero {nombre:'Accion'})
CREATE (s)-[:PERTENECE_A]->(g)
MATCH (s:Series {title:'The Flash'}), (g:Genero {nombre:'Ciencia Ficcion'})
CREATE (s)-[:PERTENECE_A]->(g)
MATCH (s:Series {title:'The Flash'}), (g:Genero {nombre:'Superheroes'})
CREATE (s)-[:PERTENECE_A]->(g)

CREATE (Arrow:Series {title:'Arrow', released:2012, tagline:'Archer Hero'})
MATCH (s:Series {title:'Arrow'}), (g:Genero {nombre:'Accion'})
CREATE (s)-[:PERTENECE_A]->(g)
MATCH (s:Series {title:'Arrow'}), (g:Genero {nombre:'Superheroes'})
CREATE (s)-[:PERTENECE_A]->(g)


CREATE (Gotham:Series {title:'Gotham', released:2014, tagline:'Batman Prequel'})
MATCH (s:Series {title:'Gotham'}), (g:Genero {nombre:'Accion'})
CREATE (s)-[:PERTENECE_A]->(g)
MATCH (s:Series {title:'Gotham'}), (g:Genero {nombre:'Superheroes'})
CREATE (s)-[:PERTENECE_A]->(g)
MATCH (s:Series {title:'Gotham'}), (g:Genero {nombre:'Crimen'})
CREATE (s)-[:PERTENECE_A]->(g)

CREATE (Daredevil:Series {title:'Daredevil', released:2015, tagline:'Blind Hero'})
MATCH (s:Series {title:'Daredevil'}), (g:Genero {nombre:'Accion'})
CREATE (s)-[:PERTENECE_A]->(g)
MATCH (s:Series {title:'Daredevil'}), (g:Genero {nombre:'Superheroes'})
CREATE (s)-[:PERTENECE_A]->(g)
MATCH (s:Series {title:'Daredevil'}), (g:Genero {nombre:'Crimen'})
CREATE (s)-[:PERTENECE_A]->(g)

CREATE (JessicaJones:Series {title:'Jessica Jones', released:2015, tagline:'Private Eye Hero'})
MATCH (s:Series {title:'Jessica Jones'}), (g:Genero {nombre:'Accion'})
CREATE (s)-[:PERTENECE_A]->(g)
MATCH (s:Series {title:'Jessica Jones'}), (g:Genero {nombre:'Superheroes'})
CREATE (s)-[:PERTENECE_A]->(g)
MATCH (s:Series {title:'Jessica Jones'}), (g:Genero {nombre:'Crimen'})
CREATE (s)-[:PERTENECE_A]->(g)

CREATE (LukeCage:Series {title:'Luke Cage', released:2016, tagline:'Bulletproof Hero'})
MATCH (s:Series {title:'Luke Cage'}), (g:Genero {nombre:'Accion'})
CREATE (s)-[:PERTENECE_A]->(g)
MATCH (s:Series {title:'Luke Cage'}), (g:Genero {nombre:'Superheroes'})
CREATE (s)-[:PERTENECE_A]->(g)
MATCH (s:Series {title:'Luke Cage'}), (g:Genero {nombre:'Crimen'})
CREATE (s)-[:PERTENECE_A]->(g)

CREATE (IronFist:Series {title:'Iron Fist', released:2017, tagline:'Kung Fu Hero'})
MATCH (s:Series {title:'Iron Fist'}), (g:Genero {nombre:'Accion'})
CREATE (s)-[:PERTENECE_A]->(g)
MATCH (s:Series {title:'Iron Fist'}), (g:Genero {nombre:'Superheroes'})
CREATE (s)-[:PERTENECE_A]->(g)
MATCH (s:Series {title:'Iron Fist'}), (g:Genero {nombre:'Crimen'})
CREATE (s)-[:PERTENECE_A]->(g)


CREATE (TheDefenders:Series {title:'The Defenders', released:2017, tagline:'Superhero Team-Up'})
MATCH (s:Series {title:'The Defenders'}), (g:Genero {nombre:'Accion'})
CREATE (s)-[:PERTENECE_A]->(g)
MATCH (s:Series {title:'The Defenders'}), (g:Genero {nombre:'Superheroes'})
CREATE (s)-[:PERTENECE_A]->(g)
MATCH (s:Series {title:'The Defenders'}), (g:Genero {nombre:'Crimen'})
CREATE (s)-[:PERTENECE_A]->(g)


CREATE (ThePunisher:Series {title:'The Punisher', released:2017, tagline:'Vigilante Hero'})
MATCH (s:Series {title:'The Punisher'}), (g:Genero {nombre:'Accion'})
CREATE (s)-[:PERTENECE_A]->(g)
MATCH (s:Series {title:'The Punisher'}), (g:Genero {nombre:'Superheroes'})
CREATE (s)-[:PERTENECE_A]->(g)
MATCH (s:Series {title:'The Punisher'}), (g:Genero {nombre:'Crimen'})
CREATE (s)-[:PERTENECE_A]->(g)

CREATE (AgentsOfShield:Series {title:'Agents of Shield', released:2013, tagline:'Superhero Agents'})
MATCH (s:Series {title:'Agents of Shield'}), (g:Genero {nombre:'Accion'})
CREATE (s)-[:PERTENECE_A]->(g)
MATCH (s:Series {title:'Agents of Shield'}), (g:Genero {nombre:'Superheroes'})
CREATE (s)-[:PERTENECE_A]->(g)
MATCH (s:Series {title:'Agents of Shield'}), (g:Genero {nombre:'Ciencia Ficcion'})
CREATE (s)-[:PERTENECE_A]->(g)

CREATE (AgentCarter:Series {title:'Agent Carter', released:2015, tagline:'Peggy Carter Hero'})
MATCH (s:Series {title:'Agent Carter'}), (g:Genero {nombre:'Accion'})
CREATE (s)-[:PERTENECE_A]->(g)
MATCH (s:Series {title:'Agent Carter'}), (g:Genero {nombre:'Superheroes'})
CREATE (s)-[:PERTENECE_A]->(g)
MATCH (s:Series {title:'Agent Carter'}), (g:Genero {nombre:'Ciencia Ficcion'})
CREATE (s)-[:PERTENECE_A]->(g)


CREATE (LegendsOfTomorrow:Series {title:'Legends of Tomorrow', released:2016, tagline:'Time Travel Heroes'})
MATCH (s:Series {title:'Legends of Tomorrow'}), (g:Genero {nombre:'Accion'})
CREATE (s)-[:PERTENECE_A]->(g)
MATCH (s:Series {title:'Legends of Tomorrow'}), (g:Genero {nombre:'Superheroes'})
CREATE (s)-[:PERTENECE_A]->(g)
MATCH (s:Series {title:'Legends of Tomorrow'}), (g:Genero {nombre:'Ciencia Ficcion'})
CREATE (s)-[:PERTENECE_A]->(g)

CREATE (Supergirl:Series {title:'Supergirl', released:2015, tagline:'Kryptonian Hero'})
MATCH (s:Series {title:'Supergirl'}), (g:Genero {nombre:'Accion'})
CREATE (s)-[:PERTENECE_A]->(g)
MATCH (s:Series {title:'Supergirl'}), (g:Genero {nombre:'Superheroes'})
CREATE (s)-[:PERTENECE_A]->(g)
MATCH (s:Series {title:'Supergirl'}), (g:Genero {nombre:'Ciencia Ficcion'})
CREATE (s)-[:PERTENECE_A]->(g)

CREATE (Batwoman:Series {title:'Batwoman', released:2019, tagline:'Bat Hero'})
MATCH (s:Series {title:'Batwoman'}), (g:Genero {nombre:'Accion'})
CREATE (s)-[:PERTENECE_A]->(g)
MATCH (s:Series {title:'Batwoman'}), (g:Genero {nombre:'Superheroes'})
CREATE (s)-[:PERTENECE_A]->(g)
MATCH (s:Series {title:'Batwoman'}), (g:Genero {nombre:'Ciencia Ficcion'})
CREATE (s)-[:PERTENECE_A]->(g)

CREATE (BlackLightning:Series {title:'Black Lightning', released:2018, tagline:'Electric Hero'})
MATCH (s:Series {title:'Black Lightning'}), (g:Genero {nombre:'Accion'})
CREATE (s)-[:PERTENECE_A]->(g)
MATCH (s:Series {title:'Black Lightning'}), (g:Genero {nombre:'Superheroes'})
CREATE (s)-[:PERTENECE_A]->(g)
MATCH (s:Series {title:'Black Lightning'}), (g:Genero {nombre:'Ciencia Ficcion'})                   
CREATE (s)-[:PERTENECE_A]->(g)                  


CREATE (TheBoondocks:Series {title:'The Boondocks', released:2005, tagline:'Black Comedy'})
MATCH (s:Series {title:'The Boondocks'}), (g:Genero {nombre:'Comedia'})
CREATE (s)-[:PERTENECE_A]->(g)
MATCH (s:Series {title:'The Boondocks'}), (g:Genero {nombre:'Animacion'})
CREATE (s)-[:PERTENECE_A]->(g)


CREATE (RickAndMorty:Series {title:'Rick and Morty', released:2013, tagline:'Interdimensional Comedy'})
MATCH (s:Series {title:'Rick and Morty'}), (g:Genero {nombre:'Comedia'})
CREATE (s)-[:PERTENECE_A]->(g)
MATCH (s:Series {title:'Rick and Morty'}), (g:Genero {nombre:'Animacion'})
CREATE (s)-[:PERTENECE_A]->(g)
MATCH (s:Series {title:'Rick and Morty'}), (g:Genero {nombre:'Ciencia Ficcion'})
CREATE (s)-[:PERTENECE_A]->(g)

CREATE (BoJackHorseman:Series {title:'BoJack Horseman', released:2014, tagline:'Depressed Horse'})
MATCH (s:Series {title:'BoJack Horseman'}), (g:Genero {nombre:'Comedia'})
CREATE (s)-[:PERTENECE_A]->(g)
MATCH (s:Series {title:'BoJack Horseman'}), (g:Genero {nombre:'Animacion'})
CREATE (s)-[:PERTENECE_A]->(g)

CREATE (Archer:Series {title:'Archer', released:2009, tagline:'Spy Comedy'})
MATCH (s:Series {title:'Archer'}), (g:Genero {nombre:'Comedia'})
CREATE (s)-[:PERTENECE_A]->(g)
MATCH (s:Series {title:'Archer'}), (g:Genero {nombre:'Animacion'})
CREATE (s)-[:PERTENECE_A]->(g)


CREATE (Futurama:Series {title:'Futurama', released:1999, tagline:'Future Comedy'})
MATCH (s:Series {title:'Futurama'}), (g:Genero {nombre:'Comedia'})
CREATE (s)-[:PERTENECE_A]->(g)
MATCH (s:Series {title:'Futurama'}), (g:Genero {nombre:'Animacion'})
CREATE (s)-[:PERTENECE_A]->(g)


CREATE (AmericanDad:Series {title:'American Dad', released:2005, tagline:'Political Comedy'})
MATCH (s:Series {title:'American Dad'}), (g:Genero {nombre:'Comedia'})
CREATE (s)-[:PERTENECE_A]->(g)
MATCH (s:Series {title:'American Dad'}), (g:Genero {nombre:'Animacion'})
CREATE (s)-[:PERTENECE_A]->(g)


CREATE (BobBurgers:Series {title:'Bob Burgers', released:2011, tagline:'Family Comedy'})
MATCH (s:Series {title:'Bob Burgers'}), (g:Genero {nombre:'Comedia'})
CREATE (s)-[:PERTENECE_A]->(g)
MATCH (s:Series {title:'Bob Burgers'}), (g:Genero {nombre:'Animacion'})
CREATE (s)-[:PERTENECE_A]->(g)


CREATE (BigMouth:Series {title:'Big Mouth', released:2017, tagline:'Puberty Comedy'})
MATCH (s:Series {title:'Big Mouth'}), (g:Genero {nombre:'Comedia'})
CREATE (s)-[:PERTENECE_A]->(g)
MATCH (s:Series {title:'Big Mouth'}), (g:Genero {nombre:'Animacion'})
CREATE (s)-[:PERTENECE_A]->(g)


CREATE (CobraKai:Series {title:'Cobra Kai', released:2018, tagline:'Karate Kid Sequel'})
MATCH (s:Series {title:'Cobra Kai'}), (g:Genero {nombre:'Accion'})
CREATE (s)-[:PERTENECE_A]->(g)
MATCH (s:Series {title:'Cobra Kai'}), (g:Genero {nombre:'Drama'})
CREATE (s)-[:PERTENECE_A]->(g)
MATCH (s:Series {title:'Cobra Kai'}), (g:Genero {nombre:'Deporte'})
CREATE (s)-[:PERTENECE_A]->(g)

CREATE (CaptainTsubasa:Series {title:'Captain Tsubasa', released:2018, tagline:'Soccer Anime'})
MATCH (s:Series {title:'Captain Tsubasa'}), (g:Genero {nombre:'Animacion'})
CREATE (s)-[:PERTENECE_A]->(g)
MATCH (s:Series {title:'Captain Tsubasa'}), (g:Genero {nombre:'Deporte'})
CREATE (s)-[:PERTENECE_A]->(g)

CREATE (Haikyuu:Series {title:'Haikyuu', released:2014, tagline:'Volleyball Anime'})
MATCH (s:Series {title:'Haikyuu'}), (g:Genero {nombre:'Animacion'})
CREATE (s)-[:PERTENECE_A]->(g)
MATCH (s:Series {title:'Haikyuu'}), (g:Genero {nombre:'Deporte'})
CREATE (s)-[:PERTENECE_A]->(g)


CREATE (DeathNote:Series {title:'Death Note', released:2006, tagline:'Death God Anime'})
MATCH (s:Series {title:'Death Note'}), (g:Genero {nombre:'Animacion'})
CREATE (s)-[:PERTENECE_A]->(g)
MATCH (s:Series {title:'Death Note'}), (g:Genero {nombre:'Misterio'})
CREATE (s)-[:PERTENECE_A]->(g)
MATCH (s:Series {title:'Death Note'}), (g:Genero {nombre:'Drama'})
CREATE (s)-[:PERTENECE_A]->(g)

CREATE (Evangelion:Series {title:'Evangelion', released:1995, tagline:'Mecha Anime'})
MATCH (s:Series {title:'Evangelion'}), (g:Genero {nombre:'Animacion'})
CREATE (s)-[:PERTENECE_A]->(g)
MATCH (s:Series {title:'Evangelion'}), (g:Genero {nombre:'Ciencia Ficcion'})
CREATE (s)-[:PERTENECE_A]->(g)
MATCH (s:Series {title:'Evangelion'}), (g:Genero {nombre:'Drama'})
CREATE (s)-[:PERTENECE_A]->(g)

CREATE (AttackOnTitan:Series {title:'Attack on Titan', released:2013, tagline:'Giant Titan Anime'})
MATCH (s:Series {title:'Attack on Titan'}), (g:Genero {nombre:'Animacion'})
CREATE (s)-[:PERTENECE_A]->(g)
MATCH (s:Series {title:'Attack on Titan'}), (g:Genero {nombre:'Accion'})
CREATE (s)-[:PERTENECE_A]->(g)
MATCH (s:Series {title:'Attack on Titan'}), (g:Genero {nombre:'Fantasia'})
CREATE (s)-[:PERTENECE_A]->(g)

CREATE (OnePiece:Series {title:'One Piece', released:1999, tagline:'Pirate Anime'})
MATCH (s:Series {title:'One Piece'}), (g:Genero {nombre:'Animacion'})
CREATE (s)-[:PERTENECE_A]->(g)
MATCH (s:Series {title:'One Piece'}), (g:Genero {nombre:'Accion'})
CREATE (s)-[:PERTENECE_A]->(g)
MATCH (s:Series {title:'One Piece'}), (g:Genero {nombre:'Fantasia'})
CREATE (s)-[:PERTENECE_A]->(g)

CREATE (Naruto:Series {title:'Naruto', released:2002, tagline:'Ninja Anime'})
MATCH (s:Series {title:'Naruto'}), (g:Genero {nombre:'Animacion'})
CREATE (s)-[:PERTENECE_A]->(g)
MATCH (s:Series {title:'Naruto'}), (g:Genero {nombre:'Accion'})
CREATE (s)-[:PERTENECE_A]->(g)
MATCH (s:Series {title:'Naruto'}), (g:Genero {nombre:'Fantasia'})
CREATE (s)-[:PERTENECE_A]->(g)

CREATE (DragonBall:Series {title:'Dragon Ball', released:1986, tagline:'Saiyan Anime'})
MATCH (s:Series {title:'Dragon Ball'}), (g:Genero {nombre:'Animacion'})
CREATE (s)-[:PERTENECE_A]->(g)
MATCH (s:Series {title:'Dragon Ball'}), (g:Genero {nombre:'Accion'})
CREATE (s)-[:PERTENECE_A]->(g)
MATCH (s:Series {title:'Dragon Ball'}), (g:Genero {nombre:'Fantasia'})
CREATE (s)-[:PERTENECE_A]->(g)

CREATE (Bleach:Series {title:'Bleach', released:2004, tagline:'Shinigami Anime'})
MATCH (s:Series {title:'Bleach'}), (g:Genero {nombre:'Animacion'})
CREATE (s)-[:PERTENECE_A]->(g)
MATCH (s:Series {title:'Bleach'}), (g:Genero {nombre:'Accion'})
CREATE (s)-[:PERTENECE_A]->(g)
MATCH (s:Series {title:'Bleach'}), (g:Genero {nombre:'Fantasia'})
CREATE (s)-[:PERTENECE_A]->(g)

CREATE (MyHeroAcademia:Series {title:'My Hero Academia', released:2016, tagline:'Hero Academy Anime'})
MATCH (s:Series {title:'My Hero Academia'}), (g:Genero {nombre:'Animacion'})
CREATE (s)-[:PERTENECE_A]->(g)
MATCH (s:Series {title:'My Hero Academia'}), (g:Genero {nombre:'Accion'})
CREATE (s)-[:PERTENECE_A]->(g)
MATCH (s:Series {title:'My Hero Academia'}), (g:Genero {nombre:'Superheroes'})
CREATE (s)-[:PERTENECE_A]->(g)


CREATE (TokyoGhoul:Series {title:'Tokyo Ghoul', released:2014, tagline:'Ghoul Anime'})
MATCH (s:Series {title:'Tokyo Ghoul'}), (g:Genero {nombre:'Animacion'})
CREATE (s)-[:PERTENECE_A]->(g)
MATCH (s:Series {title:'Tokyo Ghoul'}), (g:Genero {nombre:'Accion'})
CREATE (s)-[:PERTENECE_A]->(g)
MATCH (s:Series {title:'Tokyo Ghoul'}), (g:Genero {nombre:'Horror'})
CREATE (s)-[:PERTENECE_A]->(g)

CREATE (Parasyte:Series {title:'Parasyte', released:2014, tagline:'Alien Parasite Anime'})
MATCH (s:Series {title:'Parasyte'}), (g:Genero {nombre:'Animacion'})
CREATE (s)-[:PERTENECE_A]->(g)
MATCH (s:Series {title:'Parasyte'}), (g:Genero {nombre:'Accion'})
CREATE (s)-[:PERTENECE_A]->(g)
MATCH (s:Series {title:'Parasyte'}), (g:Genero {nombre:'Horror'})
CREATE (s)-[:PERTENECE_A]->(g)

CREATE (DemonSlayer:Series {title:'Demon Slayer', released:2019, tagline:'Demon Hunter Anime'})
MATCH (s:Series {title:'Demon Slayer'}), (g:Genero {nombre:'Animacion'})
CREATE (s)-[:PERTENECE_A]->(g)
MATCH (s:Series {title:'Demon Slayer'}), (g:Genero {nombre:'Accion'})
CREATE (s)-[:PERTENECE_A]->(g)
MATCH (s:Series {title:'Demon Slayer'}), (g:Genero {nombre:'Fantasia'})
CREATE (s)-[:PERTENECE_A]->(g)


CREATE (elChavo:Series {title:'El Chavo del 8', released:1971, tagline:'Vecindad Comedy'})
MATCH (s:Series {title:'El Chavo del 8'}), (g:Genero {nombre:'Comedia'})
CREATE (s)-[:PERTENECE_A]->(g)

CREATE (elChapulin:Series {title:'El Chapulin Colorado', released:1970, tagline:'Superhero Comedy'})
MATCH (s:Series {title:'El Chapulin Colorado'}), (g:Genero {nombre:'Comedia'})
CREATE (s)-[:PERTENECE_A]->(g)
MATCH (s:Series {title:'El Chapulin Colorado'}), (g:Genero {nombre:'Superheroes'})
CREATE (s)-[:PERTENECE_A]->(g)

CREATE (LaRosaDeGuadalupe:Series {title:'La Rosa de Guadalupe', released:2008, tagline:'Drama Mexicano'})
MATCH (s:Series {title:'La Rosa de Guadalupe'}), (g:Genero {nombre:'Drama'})
CREATE (s)-[:PERTENECE_A]->(g)

CREATE (LaCasaDeLasFlores:Series {title:'La Casa de las Flores', released:2018, tagline:'Family Drama'})
MATCH (s:Series {title:'La Casa de las Flores'}), (g:Genero {nombre:'Drama'})
CREATE (s)-[:PERTENECE_A]->(g)
MATCH (s:Series {title:'La Casa de las Flores'}), (g:Genero {nombre:'Comedia'})
CREATE (s)-[:PERTENECE_A]->(g)

CREATE (ClubDeCuervos:Series {title:'Club de Cuervos', released:2015, tagline:'Soccer Drama'})
MATCH (s:Series {title:'Club de Cuervos'}), (g:Genero {nombre:'Drama'})
CREATE (s)-[:PERTENECE_A]->(g)
MATCH (s:Series {title:'Club de Cuervos'}), (g:Genero {nombre:'Deporte'})
CREATE (s)-[:PERTENECE_A]->(g)

CREATE (Ingobernable:Series {title:'Ingobernable', released:2017, tagline:'Political Drama'})
MATCH (s:Series {title:'Ingobernable'}), (g:Genero {nombre:'Drama'})
CREATE (s)-[:PERTENECE_A]->(g)
MATCH (s:Series {title:'Ingobernable'}), (g:Genero {nombre:'Crimen'})
CREATE (s)-[:PERTENECE_A]->(g)



CREATE (ElEncantadorDePerros:Series {title:'El Encantador de Perros', released:2004, tagline:'Dog Training'})
MATCH (s:Series {title:'El Encantador de Perros'}), (g:Genero {nombre:'Reality'})
CREATE (s)-[:PERTENECE_A]->(g)
MATCH (s:Series {title:'El Encantador de Perros'}), (g:Genero {nombre:'Documental'})
CREATE (s)-[:PERTENECE_A]->(g)

CREATE (Goodless:Series {title:'Goodless', released:2017, tagline:'Western Drama'})
MATCH (s:Series {title:'Goodless'}), (g:Genero {nombre:'Drama'})
CREATE (s)-[:PERTENECE_A]->(g)
MATCH (s:Series {title:'Goodless'}), (g:Genero {nombre:'Western'})
CREATE (s)-[:PERTENECE_A]->(g)

MATCH (s:Series {title:'The Crown'}), (g:Genero {nombre:'Biografia'})
CREATE (s)-[:PERTENECE_A]->(g)

CREATE (Chernobyl:Series {title:'Chernobyl', released:2019, tagline:'Nuclear Disaster'})
MATCH (s:Series {title:'Chernobyl'}), (g:Genero {nombre:'Documental'})
CREATE (s)-[:PERTENECE_A]->(g)
MATCH (s:Series {title:'Chernobyl'}), (g:Genero {nombre:'Drama'})
CREATE (s)-[:PERTENECE_A]->(g)


MATCH (s:Series {title:'Stranger Things'}), (g:Genero {nombre:'Thriller'})
CREATE (s)-[:PERTENECE_A]->(g)