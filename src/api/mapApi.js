import axios from '@/utils/axios'


export const gasMapApi = {
  
  getTotalData: () => axios.get('/map/total-data'),
  
  refreshCache: () => axios.get('/map/refresh-cache')
}