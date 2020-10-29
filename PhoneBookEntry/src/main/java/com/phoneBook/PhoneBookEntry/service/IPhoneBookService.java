package com.phoneBook.PhoneBookEntry.service;

import java.io.IOException;
import java.util.List;

import com.phoneBook.PhoneBookEntry.model.Entry;

public interface IPhoneBookService {

	public Entry getEntry(int id) throws IOException;

	public List<Entry> getAllOrderedBy(boolean byFirstName) throws IOException;

	public Entry createEntry(Entry entry) throws IOException;

	public Entry updateEntry(Entry entry) throws IOException;

	public boolean deleteEntry(int id) throws IOException;

}
