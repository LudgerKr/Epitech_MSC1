<template>
  <div>
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
          <b-timepicker style="border-color: black" v-model="startTime" locale="fr"></b-timepicker>
        </b-col>
        <b-col md="auto">
          <b-timepicker style="border-color: black" v-model="endTime" locale="fr"></b-timepicker>
        </b-col>
        <b-col md="auto">
          <b-button style="margin: 0 auto; display: block; background-color: orange;"
                    @click="updateChart(startDate, startTime, endDate, endTime)">
            Update
          </b-button>
        </b-col>
      </b-row>
      <b-row>
        <donut-chart
            id="donut"
            :data= "[
              { label: 'Employee\'s hours ', value: getTimeToHours(getTotalClockTime(clocks)) },
              { label: 'Week hours', value: 35 },
          ]"
            colors='[ "#FF6384", "#36A2EB" ]'
            resize="false">
        </donut-chart>
      </b-row>
    </b-container>
  </div>
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
  name: "AnalyticUser",
  components: {
    DonutChart
  },
  data () {
    return {
      clocks: "",
      startDate: "2020-11-02",
      endDate: "2020-11-06",
      startTime: "05:00:00",
      endTime: "20:00:00"
    }
  },
  mounted() {
    axios.all([
      axios.get('http://localhost:4001/api/v1/clocks/' + this.$router.currentRoute.params.id +
          "?start_time=" + this.startDate + " " + this.startTime +
          "&end_time=" + this.endDate + " " + this.endTime,
          config),
    ]).then(axios.spread((clocks) => {
      this.clocks = clocks.data.data;
    }));
  },
  methods: {
    timediff(time1, time2) {
      var diff =(new Date(time2) - new Date(time1)) / 1000;
      diff /= 60;
      return Math.abs(Math.round(diff));
    },
    getTotalClockTime(clocks) {
      let totalTime = 0;
      let size = clocks.length
      let i = 0;
      while(size > i) {
        totalTime += this.timediff(clocks[i].time, clocks[i + 1].time)
        i += 2
      }
      return totalTime;
    },
    getTimeToHours(totalTime) {
      return (totalTime / 60)
    },
    updateChart(startDate, startTime, endDate, endTime) {
      axios.all([
        axios.get('http://localhost:4001/api/v1/clocks/' + this.$router.currentRoute.params.id +
            "?start_time=" + startDate + " " + startTime +
            "&end_time=" + endDate + " " + endTime,
            config),
      ]).then(axios.spread((clocks) => {
        this.clocks = clocks.data.data;
      }));
    }
  }
}
</script>

<style scoped>
h1{
  margin: 10px;
  font-size: 20px;
}

</style>