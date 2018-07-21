import React from 'react';
// @material-ui/icons
import BugReport from '@material-ui/icons/BugReport';
import Code from '@material-ui/icons/Code';
import Cloud from '@material-ui/icons/Cloud';
// core components
import Tabs from 'components/CustomTabs/CustomTabs.jsx';
import SensorCandidates from '../../components/NEW/Settings/SensorCandidates';
import SensorRooms from '../../components/NEW/Settings/SensorRooms';
import SensorTypes from '../../components/NEW/Settings/SensorTypes';
import SensorProducts from '../../components/NEW/Settings/SensorProducts';
import MqttSettings from '../../components/NEW/Settings/MqttSettings';

export default class Settings extends React.Component {
	render() {
		return (
			<Tabs
				title="Settings:"
				headerColor="primary"
				tabs={[
					{
						tabName: 'Connection',
						tabIcon: Cloud,
						tabContent: <MqttSettings />
					},
					{
						tabName: 'New Sensors',
						tabIcon: Cloud,
						tabContent: <SensorCandidates />
					},

					{
						tabName: 'Rooms',
						tabIcon: Cloud,
						tabContent: <SensorRooms />
					},
					{
						tabName: 'Types',
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
