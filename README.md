# akka
   This branch contains some experimental learning i am doing with akka

 - Start of learning some basics. This is handled in the meetup package.
   A client listens to meetups rsvp stream and sends the event message over to location actor.
   I was able to learn basics like how to 
      - instantiate the actor
      - instantiate workers (as actorref)
      - use akka logging
      - "tell" to an actor
 - wikilinks package was used to pase the wiki links dataset. This is still not completely done; its a learning paused for now. I was able to understand some quirks of instantiating too many actors and hitting memory boundaries. When I come back 
 I would have to learn things like routing, paths, remoting etc to handle the scenarios.
 
