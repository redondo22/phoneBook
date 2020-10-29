package com.phoneBook.PhoneBookEntry.repository;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.phoneBook.PhoneBookEntry.model.Entry;

@Component
public class PhoneBookRepository implements IPhoneBookRepository {

	private ObjectMapper objectMapper = new ObjectMapper();

	private final String filePath = new File("source.json").getAbsolutePath();

	@Override
	public Entry getEntry(int id) throws IOException {

		List<Entry> entries = new ArrayList<Entry>();
		Entry entry = new Entry();

		entries = Arrays.asList(objectMapper.readValue(Paths.get(filePath).toFile(), Entry[].class));
		for (Entry e : entries) {
			if (e.getId() == id)
				entry = e;
		}

		return entry;
	}

	@Override
	public List<Entry> getAll() throws IOException {
		List<Entry> entries = new ArrayList<Entry>();

		entries = Arrays.asList(objectMapper.readValue(Paths.get(filePath).toFile(), Entry[].class));

		return entries;
	}

	@Override
	public Entry createEntry(Entry entry) throws IOException {

		List<Entry> entries = new ArrayList<Entry>();

		entries = Arrays.asList(objectMapper.readValue(Paths.get(filePath).toFile(), Entry[].class));

		int id = entries.size() + 1;

		entry.setId(id);

		try (BufferedWriter bWriter = new BufferedWriter(new FileWriter(Paths.get(filePath).toFile()))) {
			PrintWriter pw = new PrintWriter(bWriter);
			pw.write("[");

			for (Entry e : entries) {
				pw.println(e);
				pw.println(",");
			}

			pw.println(entry.toString());
			pw.write("]");
		} catch (Exception e) {

			e.printStackTrace();
		}

		return getEntry(id);
	}

	@Override
	public Entry updateEntry(Entry entry) throws IOException {
		int objectIndex = 0;
		int index = 0;
		List<Entry> entries = new ArrayList<Entry>();

		entries = Arrays.asList(objectMapper.readValue(Paths.get(filePath).toFile(), Entry[].class));
		objectIndex = entries.lastIndexOf(getEntry(entry.getId()));

		entries.set(objectIndex, entry);

		try (BufferedWriter bWriter = new BufferedWriter(new FileWriter(Paths.get(filePath).toFile()))) {
			PrintWriter pw = new PrintWriter(bWriter);
			pw.write("[");

			for (Entry e : entries) {
				pw.println(e);

				if (index != entries.size() - 1)
					pw.println(",");

				index++;
			}

			pw.write("]");
		} catch (Exception e) {

			e.printStackTrace();
		}

		return entry;
	}

	@Override
	public boolean deleteEntry(int id) throws IOException {
		int objectIndex = 0;
		int index = 0;
		List<Entry> entries = new ArrayList<Entry>();

		entries = Arrays.asList(objectMapper.readValue(Paths.get(filePath).toFile(), Entry[].class));

		Entry toDelete = getEntry(id);
		objectIndex = entries.lastIndexOf(getEntry(toDelete.getId()));

		if (toDelete.getId() != 0) {

			try (BufferedWriter bWriter = new BufferedWriter(new FileWriter(Paths.get(filePath).toFile()))) {

				PrintWriter pw = new PrintWriter(bWriter);
				pw.write("[");

				for (Entry e : entries) {
					if (!e.equals(toDelete)) {
						pw.println(e);

						if (index != entries.size() - 1)
							if (objectIndex == entries.size() - 1 && objectIndex == index + 1)
								pw.println("");
							else
								pw.println(",");
					}
					index++;
				}

				pw.write("]");

			} catch (Exception e) {

				e.printStackTrace();
			}

			return true;

		} else {

			return false;
		}
	}
}
