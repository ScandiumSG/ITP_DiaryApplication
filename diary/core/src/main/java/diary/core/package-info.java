/**
 * diary.core contains 3 classes; Entry, EntrySearch, and User.
 * </p><b>Entry:</b> The class contains the
 * constructor and getters for content and date of creation. No setter methods
 * are provided however, so to modify a entry a new Entry object with
 * the modified information has to be created. There is also a support method,
 * parseCurrentTime(), to rapidly provide the current system date.
 *
 * </p><b>EntrySearch:</b> A single method class, searchEntries, that allow a
 * search through every single locally stored diary. A list of all entries
 * that match a percentant (60%) of the search keywords is then returned.
 *
 * </p><b>User:</b> Class to make a User object that influence persistance
 * naming as well as allowing retrieval of json files from REST-API.
 * User is composed of both a username and a user-chosen pincode (4-digits).
 *
 * @since v1.0
 * @author Stian K. Gaustad, Lars Overskeid
 * @since Last change: 11.11.2021
 */
package diary.core;
