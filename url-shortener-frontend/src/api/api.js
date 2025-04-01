import axios from 'axios';

const BASE_URL = "http://localhost:8082";

export const createShortUrl = async (longUrl, customShortCode) => {
    try {
      const response = await axios.post('http://localhost:8082/shorten', {
        longUrl,
        customShortCode
      });
      return response.data; 
    } catch (error) {
      console.error('Error creating short URL:', error);
      throw error;
    }
};
