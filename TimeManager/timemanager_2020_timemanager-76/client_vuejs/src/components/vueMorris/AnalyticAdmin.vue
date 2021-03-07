<template>
  <div>
  <b-container>
      <b-row>
        <AnalyticManager></AnalyticManager>
      </b-row>
    </b-container>
  </div>
</template>

<script>
import VueCookies from 'vue-cookies';
import axios from 'axios';
import AnalyticManager from "@/components/vueMorris/AnalyticManager";

const config = {
  'headers': {
    'Authorization': 'Bearer ' + VueCookies.get('token'),
  }
};

export default {
  name: "AnalyticAdmin",
  components: {
    AnalyticManager
  },
  data () {
    return {
      users: "",
      times: [],
      clocks: "",
      employees: [],
      startDate: "2020-11-02",
      endDate: "2020-11-06",
      startTime: "05:00:00",
      endTime: "20:00:00"
    }
  },
  mounted() {
    axios.all([
      axios.get('http://localhost:4001/api/manager/users/teams/' + this.$router.currentRoute.params.id, config),
    ]).then(axios.spread((users) => {
      this.users = users.data.data;
    }));
  },

  methods: {
    timediff(time1, time2) {
      var diff =(new Date(time2) - new Date(time1)) / 1000;
      diff /= 60;
      return Math.abs(Math.round(diff));
    },

    parseJSON(size, response) {
      let times = [];
      for (let i = 0; i < size; i++) {
        let json = {
          time : response.data.data[i].time,
        }
        times.push(json);
      }
      return this.getAverageClockTime(times)
    },

    getAllUserId(users) {
      this.times = []
      let size = users.length
      let userIds = [];
      let i = 0;
      while(size > i) {
        userIds.push(users[i].id);
        i++;
      }
      return userIds;
    },

    getAllClockFromAllUsers(userIds) {
      let size = userIds.length;
      let i = 0;

      while (size > i) {
        axios.all([
          axios.get('http://localhost:4001/api/v1/clocks/' + userIds[i] +
              "?start_time=" + this.startDate + " " + this.startTime +
              "&end_time=" + this.endDate + " " + this.endTime,
              config),
        ]).then(axios.spread((clocks) => {
          size = clocks.data.data.length;
          parseInt(this.parseJSON(size, clocks))
        }));
        i++;
      }
    },

    getAverageClockTime(clocks) {
      let totalTime = 0;
      let i = 0;
      while (clocks[i]) {
        totalTime += this.timediff(clocks[i].time, clocks[i + 1].time)
        this.times.push(totalTime);
        i += 2;
      }
      this.employees.push(totalTime / 60);
      console.log(this.employees)
    },

    getTimeToHours(allTimes) {
      let i = 0;
      let totalTime = 0;
      let times = 0;
      while(allTimes[i]) {
        times += parseInt(allTimes[i]);
        i++;
      }
      totalTime = times / allTimes.length;
      return (totalTime / 60)
    },
  }
}
</script>

<style scoped>
h1{
  margin: 10px;
  font-size: 20px;
}

</style>