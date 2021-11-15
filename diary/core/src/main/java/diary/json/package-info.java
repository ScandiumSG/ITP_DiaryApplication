/**
 * A collection of static classes and methods that is related to persistance.
 *
 * <p><b>EntryToJSON:</b> Methods to write singular entries or entire diaries
 * provided as strings.
 *
 * <p><b>EntryFromJSON:</b> Methods to read an entire diary to a list of
 * Entry's, pick out Entry from the list of Entry's based on entry date, or
 * reading the entry json file to a string.
 *
 * <p>Some methods provide a relativePath boolean modifiers, intended behaviour is
 * for the REST-API to be able to store in root-directory instead of the
 * src/main/resources. These modifiers should be used with caution in order
 * to not generate files in "wierd" locations.
 *
 * <p><b>RetrieveDiaries:</b> Methods that either provide all diaries (as a
 * list) for associated with a provided user or that retrieve names of all
 * locally stored diaries.
 *
 * <p><b>PersitanceUtil and PersistancePaths:</b> Support methods to increase
 * modularity of the persistance package. PersistanceUtil provides File objects
 * or fileNames that begin with a provided phrase. PersistancePaths provides
 * paths for all suitable file storage locations, both in src/main/resources
 * and in root-dir (intended for REST-API applications only).
 *
 * @since v1.0
 * @author Stian K. Gaustad, Lars Overskeid
 * @since Last change: 11.11.2021
 */
package diary.json;
