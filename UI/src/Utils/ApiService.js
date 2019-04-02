import axios from 'axios'

const url = 'some_url'
const apiRequest = ((reqMethod, ...params) => reqMethod(...params).then(({ data }) => data))

const CREATE_PROFILE = data => axios.post(`${url}/profile`, data)

const GET_PROFILE = () => axios.get(`${url}/profile`)

const ApiService = {
    ROUTES: {
        GET: {
            GET_PROFILE
        },
        POST: {
            CREATE_PROFILE
        },
        PUT: {}
    },
    apiRequest
}

export default ApiService
