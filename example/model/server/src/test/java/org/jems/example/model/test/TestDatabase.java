/*******************************************************************/
/*
        File: TestDatabase.java
        
        Author: James Cowan
*/
/*******************************************************************/

package org.jems.example.model.test;

import org.jems.dao.IDao;
import org.jems.example.model.Album;
import org.jems.example.model.Artist;
import org.jems.example.model.Track;

/*******************************************************************/

public class TestDatabase extends BaseTest
{	
	/*******************************************************************/
	// public methods
	/*******************************************************************/
	/** testDatabase */
	
	public void testDatabase()throws Exception
	{
	IDao dao = getDao();
	Artist artist = new Artist();
	Album album = new Album();
	Track track = new Track();
	
		artist.setName("Jimi Hendrix");
		artist = dao.create(artist);
		album.setName("Electric Ladyland");
		album.setArtist(artist);
		album = dao.create(album);
		track.setAlbum(album);
		track.setLength(10);
		track.setName("Still Raining Still Dreaming");
		dao.create(track);
	
	} // testDatabase

	/*******************************************************************/
	// protected methods
	/*******************************************************************/

} // TestDatabase

/*******************************************************************/
