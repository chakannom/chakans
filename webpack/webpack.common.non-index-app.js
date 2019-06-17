const webpack = require('webpack');
const CopyWebpackPlugin = require('copy-webpack-plugin');
const HtmlWebpackPlugin = require('html-webpack-plugin');
const rxPaths = require('rxjs/_esm5/path-mapping');
const MergeJsonWebpackPlugin = require("merge-jsons-webpack-plugin");

const utils = require('./utils.js');

module.exports = (options) => ({
    resolve: {
        extensions: ['.ts', '.js'],
        modules: ['node_modules'],
        alias: {
            app: utils.root('src/main/webapp/' + options.appName + '/app/'),
            ...rxPaths()
        }
    },
    stats: {
        children: false
    },
    module: {
        rules: [
            {
                test: /\.(jpe?g|png|gif|svg|woff2?|ttf|eot)$/i,
                loader: 'file-loader',
                options: {
                    digest: 'hex',
                    hash: 'sha512',
                    name: 'content/[hash].[ext]'
                }
            },
            {
                test: /manifest.webapp$/,
                loader: 'file-loader',
                options: {
                    name: 'manifest.webapp'
                }
            },
            // Ignore warnings about System.import in Angular
            { test: /[\/\\]@angular[\/\\].+\.js$/, parser: { system: true } },
        ]
    },
    plugins: [
        new webpack.DefinePlugin({
            'process.env': {
                NODE_ENV: `'${options.env}'`,
                BUILD_TIMESTAMP: `'${new Date().getTime()}'`,
                VERSION: `'${utils.parseVersion()}'`,
                DEBUG_INFO_ENABLED: options.env === 'development',
                // The root URL for API calls, ending with a '/' - for example: `"https://www.jhipster.tech:8081/myservice/"`.
                // If this URL is left empty (""), then it will be relative to the current context.
                // If you use an API server, in `prod` mode, you will need to enable CORS
                // (see the `jhipster.cors` common JHipster property in the `application-*.yml` configurations)
                SERVER_API_URL: `'/'`,
                IMGPROXY_URL: `'${options.imgproxy.url}'`,
                IMGPROXY_KEY: `'${options.imgproxy.key}'`,
                IMGPROXY_SALT: `'${options.imgproxy.salt}'`
            }
        }),
        new CopyWebpackPlugin([
            { from: './src/main/webapp/' + options.appName + '/content/', to: 'content' },
            { from: './src/main/webapp/' + options.appName + '/manifest.webapp', to: 'manifest.webapp' }
        ]),
        new MergeJsonWebpackPlugin({
            output: {
                groupBy: [
                    { pattern: "./src/main/webapp/" + options.appName + "/i18n/ko/*.json", fileName: "./i18n/ko.json" },
                    { pattern: "./src/main/webapp/" + options.appName + "/i18n/en/*.json", fileName: "./i18n/en.json" }
                ]
            }
        }),
        new HtmlWebpackPlugin({
            template: './src/main/webapp/' + options.appName + '/index.html',
            chunks: ['polyfills', 'main', 'global'],
            chunksSortMode: 'manual',
            inject: 'body'
        })
    ]
});
