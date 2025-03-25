import React, { useEffect, useState } from 'react';
import { BrowserRouter as Router, Routes, Route, Link } from 'react-router-dom';
import Courses from './pages/Courses';
import Students from './pages/Students';
import './styles/App.css';
import apiService from './services/apiService'; // Import API service

const App = () => {
  const [courses, setCourses] = useState([]);

  useEffect(() => {
    // Fetch courses on initial load
    const fetchCourses = async () => {
      const coursesData = await apiService('http://localhost:8081/api').getCourses();
      setCourses(coursesData);
    };
    fetchCourses();
  }, []);

  return (
    <Router>
      <nav>
        <Link to="/courses">Courses</Link>
        <Link to="/students">Students</Link>
      </nav>
      <Routes>
        <Route path="/courses" element={<Courses />} />
        <Route
          path="/students"
          element={<Students courses={courses} />} // Pass courses as prop
        />
      </Routes>
    </Router>
  );
};

export default App;
