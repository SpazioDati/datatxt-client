**********************************************************
Java library to leverage SpazioDati's dataTXT with DBpedia
**********************************************************

`datatxt` is a library to query a dataTXT service and to enrich annotations
with queries to DBpedia SPARQL endpoint. It will automatically
convert literals to the corresponding Python types.

Visit http://spaziodati.eu/dataTXT for documentation and
examples.


API
---

    // Init client with default parameters.
    DataTXTClient dataTXTClient = new DataTXTClient();

    // Retrieve annotation for arbitrary free text.
    Annotation[] annotations = dataTXTClient.annotate("Il calcio e' uno sport molto sopravvalutato.");
    Annotation soccerAnnotation = annotations[0];
    soccerAnnotation.getStart(); // 3 - start char for annotation token
    soccerAnnotation.getEnd();   // 9 - end char for annotation token

    // Retrieve the DBpedia entity associated to the annotation
    Entity soccerEntity = soccerAnnotation.getEntity();
    // Retrieve the dcterms:subject for the entity
    soccerEntity.getValues("http://purl.org/dc/terms/subject")[0] // "http://it.dbpedia.org/resource/Categoria:Calcio"

License
-------
This software is licensed under BSD license, included as LICENSE.txt in the
source distribution.