/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import {ADD_FRAGMENT_COMPOSITION, INIT} from '../actions/types';
import {LAYOUT_DATA_ITEM_TYPE_LABELS} from '../config/constants/layoutDataItemTypeLabels';
import {LAYOUT_DATA_ITEM_TYPES} from '../config/constants/layoutDataItemTypes';
import {config} from '../config/index';

const BASIC_COMPONENT_COLLECTION_ID = 'BASIC_COMPONENT';
const DROPDOWN_FRAGMENT_ENTRY_KEY = `${BASIC_COMPONENT_COLLECTION_ID}-dropdown`;
const CONTENT_DISPLAY_COLLECTION_ID = 'content-display';

const DEFAULT_CONTENT_DISPLAY_COLLECTION = {
	fragmentCollectionId: 'collection-display',
	fragmentEntries: [],
	name: Liferay.Language.get('collection-display'),
};

export default function fragmentsReducer(fragments = [], action) {
	switch (action.type) {
		case ADD_FRAGMENT_COMPOSITION: {
			const composition = action.fragmentComposition;
			const existingCollection = fragments.find(
				(collection) =>
					collection.fragmentCollectionId ===
					composition.fragmentCollectionId
			);

			const newCollection = existingCollection
				? {
						...existingCollection,
						fragmentEntries: [
							...existingCollection.fragmentEntries,
							composition,
						],
				  }
				: {
						fragmentCollectionId: composition.fragmentCollectionId,
						fragmentEntries: [composition],
						name: composition.fragmentCollectionName,
				  };

			return [
				...fragments.filter(
					(collection) =>
						collection.fragmentCollectionId !==
						newCollection.fragmentCollectionId
				),

				newCollection,
			];
		}

		case INIT: {
			const contentDisplayCollection = fragments.find(
				(fragment) =>
					fragment.fragmentCollectionId ===
					CONTENT_DISPLAY_COLLECTION_ID
			);

			let newFragments = fragments.filter(
				(fragment) =>
					fragment.fragmentCollectionId !==
					CONTENT_DISPLAY_COLLECTION_ID
			);

			newFragments.unshift({
				fragmentCollectionId: 'layout-elements',
				fragmentEntries: [
					{
						data: {
							itemType: LAYOUT_DATA_ITEM_TYPES.container,
						},
						icon: 'container',
						itemId: 'container',
						label: LAYOUT_DATA_ITEM_TYPE_LABELS.container,
						type: 'container',
					},
					{
						data: {
							itemType: LAYOUT_DATA_ITEM_TYPES.row,
						},
						icon: 'table',
						itemId: 'row',
						label: LAYOUT_DATA_ITEM_TYPE_LABELS.row,
						type: 'row',
					},
				],
				name: Liferay.Language.get('layout-elements'),
			});

			newFragments.splice(2, 0, {
				...(contentDisplayCollection ||
					DEFAULT_CONTENT_DISPLAY_COLLECTION),

				fragmentEntries: [
					...(
						contentDisplayCollection ||
						DEFAULT_CONTENT_DISPLAY_COLLECTION
					).fragmentEntries,

					{
						data: {
							itemType: LAYOUT_DATA_ITEM_TYPES.collection,
						},
						icon: 'list',
						itemId: 'collection-display',
						label: Liferay.Language.get('collection-display'),
						type: LAYOUT_DATA_ITEM_TYPES.collection,
					},
				],
			});

			if (!config.dropdownFragmentEnabled) {
				newFragments = newFragments.map((fragmentCollection) => {
					if (
						fragmentCollection.fragmentCollectionId ===
						BASIC_COMPONENT_COLLECTION_ID
					) {
						return {
							...fragmentCollection,
							fragmentEntries: fragmentCollection.fragmentEntries.filter(
								(fragment) =>
									fragment.fragmentEntryKey !==
									DROPDOWN_FRAGMENT_ENTRY_KEY
							),
						};
					}

					return fragmentCollection;
				});
			}

			return newFragments;
		}

		default:
			return fragments;
	}
}
