import axios from 'axios'

const url = 'http://localhost:8080'
const apiRequest = ((reqMethod, ...params) => reqMethod(...params).then(({ data }) => data))

const CREATE_PROFILE = data => axios.post(`${url}/profile`, data)

const GET_PROFILE = () => axios.get(`${url}/profiles`)
const GET_STATUS = () => axios.get(`${url}/status`)

const ApiService = {
    ROUTES: {
        GET: {
            GET_PROFILE,
            GET_STATUS
        },
        POST: {
            CREATE_PROFILE
        },
        PUT: {}
    },
    apiRequest
}

export default ApiService
