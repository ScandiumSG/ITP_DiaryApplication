/**
 * diary.core contains only a Entry.java class. The class only contain
 * the constructor and getters for content and date of creation.
 * To modify a entry a new Entry object with the modified information has
 * to be created.
 *
 * The Entry constructor can either contain a content string, or a content
 * and a date string. If no date is provided the current date is set
 * automatically based on LocalDateTime.now(). Content string is not validated,
 * but the Date string must be of the format dd-MM-yyyy
 *
 * @since 1.0
 * @author Stian K. Gaustad, Lars Overskeid
 */
package diary.core;
