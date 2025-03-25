import axios from 'axios';

const apiService = (baseURL) => {
  const apiClient = axios.create({ baseURL });

  return {
    getAll: async () => (await apiClient.get('')).data,
    getById: async (id) => (await apiClient.get(`/${id}`)).data,
    create: async (data) => (await apiClient.post('', data)).data,
    update: async (id, data) => (await apiClient.put(`/${id}`, data)).data,
    delete: async (id) => (await apiClient.delete(`/${id}`)),
    getCourses: async () => (await apiClient.get('/courses')).data, // Fetch courses
  };
};

export default apiService;
