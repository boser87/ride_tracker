package com.pluralsight.repository;

import com.pluralsight.model.Ride;
import com.pluralsight.repository.util.RideRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Repository("rideRepository")
public class RideRepositoryImpl implements RideRepository {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public List<Ride> getRides() {
/*		Ride ride = new Ride();
		ride.setName("Corner Canyon");
		ride.setDuration(120);
		List <Ride> rides = new ArrayList<>();
		rides.add(ride);*/

		List<Ride> rides = jdbcTemplate.query("select * from ride", new RideRowMapper());

		return rides;
	}

	@Override
	public Ride createRide(Ride ride) {
	    // update is used for insert update and delete
        // uses the standard prepared statement approach

		//jdbcTemplate.update("insert into ride (name, duration) values (?,?)", ride.getName(), ride.getDuration());

        // returns the autogenerated key from DB
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                // return autogenerated id
                PreparedStatement ps = connection.prepareStatement("insert into ride (name, duration) values (?,?)", new String[]{"id"});
                ps.setString(1, ride.getName());
                ps.setInt(2, ride.getDuration());

                return ps;
            }
        }, keyHolder);

        Number id = keyHolder.getKey();

        return getRide(id.intValue());

/*		SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate);

		List<String> columns = new ArrayList<>();
		columns.add("name");
		columns.add("duration");

		simpleJdbcInsert.setTableName("ride");

		simpleJdbcInsert.setColumnNames(columns);

		Map<String, Object> data = new HashMap<>();
		data.put("name", ride.getName());
		data.put("duration", ride.getDuration());

		// in this case we can return the key that was created automatically from the db

		simpleJdbcInsert.setGeneratedKeyName("id");

		Number key = simpleJdbcInsert.executeAndReturnKey(data);

		System.out.println(key);*/
	}

	@Override
    public Ride getRide(Integer id) {
        Ride ride = jdbcTemplate.queryForObject("select * from ride where id = ?", new RideRowMapper(), id);
        return ride;
    }

}
