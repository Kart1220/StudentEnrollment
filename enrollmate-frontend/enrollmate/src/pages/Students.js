import React from 'react';
import CrudTable from '../components/CrudTable';
import apiService from '../services/apiService';

const Students = (courses) => {
  const studentApi = apiService('http://localhost:8082/api/students');
  return <CrudTable title="Students" api={studentApi}/>;
};

export default Students;
