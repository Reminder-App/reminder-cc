package br.unb.cic.reminders.model.db;

import java.util.List;

import br.unb.cic.framework.persistence.DBException;
import br.unb.cic.reminders.model.Category;

public interface CategoryDAO {

	/**
	 * List all Categories of the Reminders database.
	 *
	 * @return list of registered categories.
	 * @throws DBException
	 *             if anything goes wrong with the database query
	 */
	public List<Category> listCategories() throws DBException;

	/**
	 * Find a category by name
	 *
	 * @param name
	 *            category name
	 * @return category that satisfies the name criteria
	 */
	public Category findCategory(String name) throws DBException;

	/**
	 * Find a category by id
	 *
	 * @param id
	 *            category id
	 * @return category that satisfies the id criteria
	 */
	public Category findCategoryById(Long id) throws DBException;

	//#ifdef manageCategory
	public void saveCategory(Category category) throws DBException;

	public void updateCategory(Category category) throws DBException;

	public void deleteCategory(Category category) throws DBException;
	//#endif
}