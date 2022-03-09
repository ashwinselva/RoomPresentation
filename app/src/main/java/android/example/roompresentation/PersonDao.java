package android.example.roompresentation;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface PersonDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Person person);

    //@Update
    //void updatePerson(Person person);

    @Query("DELETE FROM person_table WHERE name LIKE '%' || :query ||'%' OR email LIKE '%' || :query ||'%' ")
    void deletePerson(String query);

    @Query("SELECT * FROM person_table WHERE name LIKE :query OR email LIKE :query")
    Person search(String query);

}
