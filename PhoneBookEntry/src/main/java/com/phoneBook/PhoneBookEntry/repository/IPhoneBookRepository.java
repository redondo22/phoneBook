package com.phoneBook.PhoneBookEntry.repository;

import java.io.IOException;
import java.util.List;

import com.phoneBook.PhoneBookEntry.model.Entry;

public interface IPhoneBookRepository {

	public Entry getEntry(int id) throws IOException;

	public List<Entry> getAll() throws IOException;

	public Entry createEntry(Entry entry) throws IOException;

	public Entry updateEntry(Entry entry) throws IOException;

	public boolean deleteEntry(int id) throws IOException;

}
