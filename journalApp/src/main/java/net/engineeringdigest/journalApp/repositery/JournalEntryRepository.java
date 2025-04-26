package net.engineeringdigest.journalApp.repositery;


import net.engineeringdigest.journalApp.entity.JournalEntry;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import static jdk.nashorn.internal.runtime.regexp.joni.Config.log;

//contoller ----> services ----> repository
public interface JournalEntryRepository extends MongoRepository<JournalEntry, ObjectId> {
  // log.info("Saving in MongoREpository");
}
