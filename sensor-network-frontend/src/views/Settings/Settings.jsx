import React from 'react';
import Code from '@material-ui/icons/Code';
import Input from '@material-ui/icons/Input';
import Tabs from 'components/MaterialUI/CustomTabs/CustomTabs.jsx';
import SensorCandidates from '../../components/Settings/SensorCandidates';
import SensorRooms from '../../components/Settings/SensorRooms';
import SensorTypes from '../../components/Settings/SensorTypes';
import SensorProducts from '../../components/Settings/SensorProducts';
import MqttSettings from '../../components/Settings/MqttSettings';

export default class Settings extends React.Component {
	render() {
		return (
			<Tabs
				title="Settings:"
				headerColor="primary"
				tabs={[
					{
						tabName: 'Connection',
						tabIcon: Input,
						tabContent: <MqttSettings />
					},
					{
						tabName: 'Sensors',
						tabIcon: Code,
						tabContent: <SensorCandidates />
					},
					{
						tabName: 'Rooms',
						tabIcon: Code,
						tabContent: <SensorRooms />
					},
					{
						tabName: 'Types',
						tabIcon: Code,
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
