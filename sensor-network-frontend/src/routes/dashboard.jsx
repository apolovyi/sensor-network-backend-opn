import Dashboard from '@material-ui/icons/Dashboard';
import Settings from '@material-ui/icons/Settings';
import SettingsPage from 'views/Settings/Settings.jsx';
import DashboardPageCouchDB from 'views/Dashboard/DashboardCouch.jsx';
import DashboardPageInfluxDB from 'views/Dashboard/DashboardInflux.jsx';

const dashboardRoutes = [
	{
		path: '/settings',
		sidebarName: 'Settings',
		navbarName: 'MQTT Settings',
		icon: Settings,
		component: SettingsPage
	},
	{
		path: '/dashboardCouch',
		sidebarName: 'Dashboard CouchDB',
		navbarName: 'Sensor Dashboard CouchDB',
		icon: Dashboard,
		component: DashboardPageCouchDB
	},
	{
		path: '/dashboardInflux',
		sidebarName: 'Dashboard InfluxDB',
		navbarName: 'Sensor Dashboard InfluxDB',
		icon: Dashboard,
		component: DashboardPageInfluxDB
	},
	{ redirect: true, path: '/', to: '/dashboardCouch', navbarName: 'Redirect' }
];

export default dashboardRoutes;
