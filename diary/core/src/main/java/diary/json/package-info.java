/**
 * Classes containing static methods to read and write to/from JSON.
 * EntryToJSON.write() will only write the content of a single entry to a file,
 * either appending new content or overwriting existing content at the
 * specified date.
 *
 * <p>EntryFromJSON.read() returns a List of every Entry found in the specific
 * JSON file if no date is provided, if a date is provided only the entry
 * from the specified date is returned.
 * @since 1.0
 * @author Stian K. Gaustad, Lars Overskeid
 */
package diary.json;
