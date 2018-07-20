import React from 'react';
// @material-ui/icons
import BugReport from '@material-ui/icons/BugReport';
import Code from '@material-ui/icons/Code';
import Cloud from '@material-ui/icons/Cloud';
// core components
import Tabs from 'components/CustomTabs/CustomTabs.jsx';
import TemporarySensors from '../../components/NEW/Settings/TemporaySensors';
import Topics from '../../components/NEW/Settings/Topics';
import SensorRooms from '../../components/NEW/Settings/SensorRooms';
import SensorTypes from '../../components/NEW/Settings/SensorTypes';
import SensorProducts from '../../components/NEW/Settings/SensorProducts';
import MqttSettings from '../../components/NEW/Settings/MqttSettings';

export default class Settings extends React.Component {
	render() {
		return (
			<Tabs
				title="Settings:"
				headerColor="info"
				tabs={[
					{
						tabName: 'Connection',
						tabIcon: Cloud,
						tabContent: <MqttSettings />
					},
					{
						tabName: 'New Sensors',
						tabIcon: Cloud,
						tabContent: <TemporarySensors />
					},

					{
						tabName: 'Sensor Rooms',
						tabIcon: Cloud,
						tabContent: <SensorRooms />
					},
					{
						tabName: 'Sensor Types',
						tabIcon: Cloud,
						tabContent: <SensorTypes />
					},
					{
						tabName: 'Sensor Products',
						tabIcon: Code,
						tabContent: <SensorProducts />
					}
				]}
			/>
		);
	}
}
