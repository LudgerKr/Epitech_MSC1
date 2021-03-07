import axios from 'axios';


export async function getUser(id, config){
    let t;
    await axios.get('http://13.68.159.231:4001/api/v1/profile/' + id, config)
         .then(resp => t = resp.data.data)
    return t
}