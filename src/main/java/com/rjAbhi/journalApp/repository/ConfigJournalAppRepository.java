package com.rjAbhi.journalApp.repository;

import com.rjAbhi.journalApp.entity.ConfigJournalAppEntity;
import com.rjAbhi.journalApp.entity.JournalEntry;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfigJournalAppRepository extends MongoRepository<ConfigJournalAppEntity, ObjectId> {


}
