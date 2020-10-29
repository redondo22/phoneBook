package com.phoneBook.PhoneBookEntry.service;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.phoneBook.PhoneBookEntry.model.Entry;
import com.phoneBook.PhoneBookEntry.repository.IPhoneBookRepository;

@RestController
public class PhoneBookService implements IPhoneBookService {

	@Autowired
	private final IPhoneBookRepository phoneBookRepo;

	public PhoneBookService(IPhoneBookRepository phoneBookRepo) {
		super();
		this.phoneBookRepo = phoneBookRepo;
		
		try {
			deleteEntry(5);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	@GetMapping("/entries/{id}")
	public Entry getEntry(@PathVariable int id) throws IOException {
		Entry entry = phoneBookRepo.getEntry(id);
		return entry;
	}

	@Override
	@GetMapping("/entries")
	public List<Entry> getAllOrderedBy(
			@RequestParam(value = "byFirstName", required = false, defaultValue = "true") boolean byFirstName)
			throws IOException {

		List<Entry> entries = phoneBookRepo.getAll();

		if (byFirstName) {
			Collections.sort(entries, Entry.ByFirstNameComparator);
		} else {
			Collections.sort(entries, Entry.ByLastNameComparator);
		}

		return entries;
	}

	@Override
	@PostMapping("/entries")
	public Entry createEntry(@RequestBody Entry entry) throws IOException {
		Entry e = phoneBookRepo.createEntry(entry);
		return e;
	}

	@Override
	@PutMapping("/entries")
	public Entry updateEntry(@RequestBody Entry entry) throws IOException {
		Entry e = phoneBookRepo.updateEntry(entry);
		return e;
	}

	@Override
	@DeleteMapping("/entries/{id}")
	public boolean deleteEntry(@PathVariable int id) throws IOException {
		boolean deleted = phoneBookRepo.deleteEntry(id);
		return deleted;
	}

}
