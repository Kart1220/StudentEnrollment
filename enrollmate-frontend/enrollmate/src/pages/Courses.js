import React from 'react';
import CrudTable from '../components/CrudTable';
import apiService from '../services/apiService';

const Courses = () => {
  const courseApi = apiService('http://localhost:8081/api/courses');
  return <CrudTable title="Courses" api={courseApi} />;
};

export default Courses;
