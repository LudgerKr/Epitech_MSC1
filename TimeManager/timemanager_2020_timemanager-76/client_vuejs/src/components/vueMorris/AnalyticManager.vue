<template>
  <b-container>
    <b-row>
      <b-col md="auto">
        <b-datepicker style="border-color: black" v-model="startDate" locale="fr"></b-datepicker>
      </b-col>
      <b-col md="auto">
        <b-datepicker style="border-color: black" v-model="endDate" locale="fr"></b-datepicker>
      </b-col>
    </b-row>
    <b-row>
      <b-col md="auto">
        <b-timepicker style="border-color: black"  v-model="startTime" locale="fr"></b-timepicker>
      </b-col>
      <b-col md="auto">
        <b-timepicker style="border-color: black" v-model="endTime" locale="fr"></b-timepicker>
      </b-col>
      <b-col md="auto">
        <b-button style="margin: 0 auto; display: block; background-color: orange;"
                  @click="(getAverageClockTime(getAllClockFromAllUsers(getAllUserId(users))))">
          Update
        </b-button>
      </b-col>
    </b-row>
    <b-row>
      <donut-chart
          id="donut"
          :data= "[
              { label: 'Hours employee 1', value: employees[0] },
              { label: 'Hours employee 2', value: employees[1] },
              { label: 'Hours employee 3', value: employees[2] },
              { label: 'Hours employee 4', value: employees[3] },
          ]"
          colors='[ "#FF6384", "#36A2EB", "#33B2FF", "6BFF33" ]'
          resize="false">
      </donut-chart>
    </b-row>
  </b-container>
</template>

<script>
import VueCookies from 'vue-cookies';
import axios from 'axios';
import { DonutChart } from 'vue-morris'
import Raphael from 'raphael/raphael'
global.Raphael = Raphael

const config = {
  'headers': {
    'Authorization': 'Bearer ' + VueCookies.get('token'),
  }
};

export default {
  name: "AnalyticManager",
  components: {
    DonutChart,
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
    console.log(this.$router.currentRoute.params.team_id)
    axios.all([
      axios.get('http://localhost:4001/api/manager/users/teams/' + this.$router.currentRoute.params.team_id, config),
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
      this.employees = []
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
      this.times = []
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
    getUser: function(params) {
      this.user.firstname = params.firstname;
      this.user.lastname = params.lastname;
      this.user.username = params.username;
      this.user.email = params.email;
      this.user.role_id = params.role_id;
      this.user.team_id = params.team_id;
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