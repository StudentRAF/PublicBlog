const env = import.meta.env;

export const Config = {
  API_URL: `http://localhost:8080${env.BASE_URL}${env.PB_API_PATH}`,
  BASE_URL: env.BASE_URL
}
