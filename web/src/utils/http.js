import axios from 'axios';

const baseURL = process.env.VUE_APP_API;

const http = axios.create({
  baseURL,
});

export default http;
