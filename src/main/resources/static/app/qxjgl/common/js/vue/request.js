var request = axios.create({
  baseURL: '/',
  timeout: 10000 // request timeout
})
request.interceptors.request.use(function(config){
        return config
  },
    function(error){
        return Promise.reject(error)
    }
)

request.interceptors.response.use(
    function(res){
        return res.data
    },
    function(error){
        return Promise.reject(error)
    }
)
