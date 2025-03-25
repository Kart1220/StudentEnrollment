import React, { useState, useEffect } from 'react';
import '../styles/CurdTable.css';

const CrudTable = ({ title, api, courses }) => {
  const [data, setData] = useState([]);
  const [form, setForm] = useState({});
  const [editingId, setEditingId] = useState(null);

  useEffect(() => {
    api.getAll().then(setData);
  }, [api]);

  const handleDelete = (id) => {
    api.delete(id).then(() => setData(data.filter((item) => item.id !== id)));
  };

  const handleSave = () => {
    const saveAction = editingId ? api.update(editingId, form) : api.create(form);
    saveAction.then((response) => {
      if (editingId) {
        setData(data.map((item) => (item.id === editingId ? response : item)));
      } else {
        setData([...data, response]);
      }
      setForm({});
      setEditingId(null);
    });
  };

  const handleEdit = (item) => {
    setEditingId(item.id);
    setForm(item); // Fill form with existing data
  };

  return (
    <div>
      <h2>{title}</h2>
      <table>
        <thead>
          <tr>
            {Object.keys(data[0] || {}).map((key) => (
              <th key={key}>{key}</th>
            ))}
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          {data.map((item) => (
            <tr key={item.id}>
              {Object.values(item).map((value, idx) => (
                <td key={idx}>{value}</td>
              ))}
              <td>
                <button onClick={() => handleEdit(item)}>Edit</button>
                <button onClick={() => handleDelete(item.id)}>Delete</button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>

      <h3>{editingId ? 'Edit' : 'Add'} Item</h3>
      {Object.keys(data[0] || {}).map((key) =>
        key !== 'id' && key !== 'courseId' && key !== 'courses' && key !== 'students' ? ( // Exclude id and courseId if not needed
          <div key={key}>
            <label>{key}:</label>
            <input
              value={form[key] || ''}
              onChange={(e) => setForm({ ...form, [key]: e.target.value })}
            />
          </div>
        ) : null
      )}
      {courses && (
        <div>
          <label>Course:</label>
          <select
            value={form.courseId || ''}
            onChange={(e) => setForm({ ...form, courseId: e.target.value })}
          >
            <option value="">Select a Course</option>
            {courses.map((course) => (
              <option key={course.id} value={course.id}>
                {course.name}
              </option>
            ))}
          </select>
        </div>
      )}
      <button onClick={handleSave}>{editingId ? 'Update' : 'Save'}</button>
    </div>
  );
};

export default CrudTable;
